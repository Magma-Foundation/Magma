package magma.compile.lang;

import magma.compile.rule.EmptyRule;
import magma.compile.rule.LazyRule;
import magma.compile.rule.NumberRule;
import magma.compile.rule.OrRule;
import magma.compile.rule.Rule;
import magma.compile.rule.SymbolRule;
import magma.compile.rule.TypeRule;
import magma.compile.rule.split.FirstRule;
import magma.compile.rule.split.LastRule;
import magma.compile.rule.split.MembersSplitter;
import magma.compile.rule.split.ParamSplitter;
import magma.compile.rule.split.SplitMultipleRule;
import magma.compile.rule.split.SplitOnceRule;
import magma.compile.rule.text.LeftRule;
import magma.compile.rule.text.RightRule;
import magma.compile.rule.text.StripRule;
import magma.compile.rule.text.extract.ExtractNodeRule;
import magma.compile.rule.text.extract.ExtractStringRule;
import magma.compile.rule.text.extract.SimpleExtractStringListRule;

import java.util.List;
import java.util.Optional;

public class JavaLang {
    public static Rule createRootRule() {
        var namespace = new TypeRule("namespace", new SimpleExtractStringListRule("namespace", "\\."));
        var modifiers = new StripRule(new SimpleExtractStringListRule("modifiers", " "));

        var value = new LazyRule();

        var arguments = new OrRule(List.of(
                new EmptyRule(),
                new SplitMultipleRule(new ParamSplitter(), "", "arguments", new StripRule(value)))
        );

        var caller = new ExtractNodeRule("caller", value);

        var invocation = new TypeRule("invocation", new RightRule(new InvocationStart(caller, arguments), ")"));

        value.setRule(new OrRule(List.of(
                new TypeRule("string", new LeftRule("\"", new RightRule(new ExtractStringRule("value"), "\""))),
                new TypeRule("lambda", new FirstRule(new StripRule(new ExtractStringRule("param-name")), "->", new StripRule(new ExtractNodeRule("value", value)))),
                invocation,
                new TypeRule("access", new LastRule(new ExtractNodeRule("parent", value), ".", new ExtractStringRule("child"))),
                new TypeRule("symbol", new SymbolRule(new ExtractStringRule("value"))),
                new TypeRule("number", new NumberRule(new ExtractStringRule("value"))),
                new TypeRule("any", new ExtractStringRule("value"))
        )));

        var type = new LazyRule();
        type.setRule(new OrRule(List.of(
                new TypeRule("array", new RightRule(new ExtractNodeRule("child", type), "[]")),
                new TypeRule("symbol", new ExtractStringRule("value"))
        )));

        var withoutModifiers = new ExtractNodeRule("type", type);
        var withModifiers = new LastRule(new SimpleExtractStringListRule("modifiers", " "), " ", withoutModifiers);
        var anyModifiers = new OrRule(List.of(withModifiers, withoutModifiers));
        var definitionHeader = new LastRule(anyModifiers, " ", new ExtractStringRule("name"));
        var definition = new TypeRule("definition", definitionHeader);
        var declaration = new TypeRule("declaration", new FirstRule(new StripRule(definitionHeader), "=", new RightRule(new StripRule(new ExtractNodeRule("value", value)), ";")));

        var statement = new LazyRule();
        statement.setRule(new OrRule(List.of(
                new TypeRule("try", new LeftRule("try ", new StripRule(new LeftRule("{", new RightRule(new ExtractNodeRule("child", createBlock(statement)), "}"))))),
                declaration,
                new TypeRule("invocation", new RightRule(invocation, ";")),
                new TypeRule("any", new ExtractStringRule("value"))
        )));

        var classMember = new OrRule(List.of(
                declaration,
                new TypeRule("method", new FirstRule(definitionHeader, "(", new FirstRule(new ExtractNodeRule("params", definition), ")", new StripRule(new LeftRule("{", new RightRule(new ExtractNodeRule("child", createBlock(statement)), "}")))))),
                new TypeRule("any", new ExtractStringRule("value"))
        ));
        var classChild = createBlock(classMember);

        var rootMember = new OrRule(List.of(
                new TypeRule("package", new LeftRule("package ", new RightRule(new ExtractNodeRule("internal", namespace), ";"))),
                new TypeRule("import", new LeftRule("import ", new RightRule(new ExtractNodeRule("external", namespace), ";"))),
                new TypeRule("class", new FirstRule(modifiers, "class ", new FirstRule(new StripRule(new ExtractStringRule("name")), "{", new RightRule(new ExtractNodeRule("child", classChild), "}")))),
                new TypeRule("any", new ExtractStringRule("value"))
        ));

        return createBlock(rootMember);
    }

    private static TypeRule createBlock(Rule child) {
        return new TypeRule("block", new SplitMultipleRule(new MembersSplitter(), "", "children", new StripRule(child)));
    }

    private static class InvocationStart extends SplitOnceRule {
        public InvocationStart(Rule caller, Rule arguments) {
            super(caller, "(", arguments);
        }

        @Override
        protected Optional<Integer> computeIndex(String input) {
            var depth = 0;

            for (int i = input.length() - 1; i >= 0; i--) {
                var c = input.charAt(i);
                if (c == '(' && depth == 0) return Optional.of(i);
                else if (c == ')') depth++;
                else if (c == '(') depth--;
            }

            return Optional.empty();
        }
    }
}
