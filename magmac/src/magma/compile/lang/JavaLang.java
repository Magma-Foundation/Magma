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
        var constructor = new TypeRule("constructor", new LeftRule("new ", new RightRule(new InvocationStart(caller, arguments), ")")));

        value.setRule(new OrRule(List.of(
                new TypeRule("string", new LeftRule("\"", new RightRule(new ExtractStringRule("value"), "\""))),
                new TypeRule("char", new LeftRule("'", new RightRule(new ExtractStringRule("value"), "'"))),
                new TypeRule("lambda", new FirstRule(new StripRule(new ExtractStringRule("param-name")), "->", new StripRule(new ExtractNodeRule("value", value)))),
                new TypeRule("ternary", new FirstRule(
                        new StripRule(new ExtractNodeRule("condition", value)), "?",
                        new FirstRule(
                                new StripRule(new ExtractNodeRule("true", value)), ":",
                                new StripRule(new ExtractNodeRule("false", value))))),
                constructor,
                invocation,
                new TypeRule("access", new LastRule(new ExtractNodeRule("parent", value), ".", new StripRule(new SymbolRule(new ExtractStringRule("child"))))),
                new TypeRule("symbol", new SymbolRule(new ExtractStringRule("value"))),
                new TypeRule("number", new NumberRule(new ExtractStringRule("value"))),
                createOperator("equals", "==", value),
                createOperator("add", "+", value),
                createOperator("greater-than", ">", value)
        )));

        var type = new LazyRule();
        type.setRule(new OrRule(List.of(
                new TypeRule("array", new RightRule(new ExtractNodeRule("child", type), "[]")),
                new TypeRule("symbol", new ExtractStringRule("value"))
        )));

        var withoutModifiers = new ExtractNodeRule("type", type);
        var withModifiers = new LastRule(new SimpleExtractStringListRule("modifiers", " "), " ", withoutModifiers);
        var anyModifiers = new OrRule(List.of(withModifiers, withoutModifiers));
        var definitionHeader = new LastRule(anyModifiers, " ", new StripRule(new SymbolRule(new ExtractStringRule("name"))));
        var definition = new TypeRule("definition", definitionHeader);
        var declaration = new TypeRule("declaration", new FirstRule(new StripRule(definitionHeader), "=", new RightRule(new StripRule(new ExtractNodeRule("value", value)), ";")));

        var statement = new LazyRule();
        var block = new RightRule(new ExtractNodeRule("child", createBlock(statement)), "}");

        statement.setRule(new OrRule(List.of(
                new TypeRule("comment", new LeftRule("//", new ExtractStringRule("value"))),
                new TypeRule("try", new LeftRule("try ", new StripRule(new LeftRule("{", block)))),
                declaration,
                new TypeRule("constructor", new RightRule(constructor, ";")),
                new TypeRule("assignment", new FirstRule(new StripRule(new SymbolRule(new ExtractStringRule("reference"))), "=", new RightRule(new StripRule(new ExtractNodeRule("value", value)), ";"))),
                new TypeRule("invocation", new RightRule(invocation, ";")),
                new TypeRule("catch", new LeftRule("catch ", new StripRule(new FirstRule(new StripRule(new LeftRule("(", new RightRule(new ExtractNodeRule("definition", new TypeRule("definition", definitionHeader)), ")"))), "{", new RightRule(new ExtractNodeRule("child", createBlock(statement)), "}"))))),
                new TypeRule("if", new LeftRule("if", new FirstRule(new StripRule(new LeftRule("(", new RightRule(new ExtractNodeRule("condition", value), ")"))), "{", block))),
                new TypeRule("return", new LeftRule("return", new RightRule(new StripRule(new OrRule(List.of(new EmptyRule(), new ExtractNodeRule("child", value)))), ";"))),
                new TypeRule("for", new LeftRule("for", new FirstRule(new StripRule(new LeftRule("(", new RightRule(new LastRule(new StripRule(definitionHeader), ":", new StripRule(new ExtractNodeRule("collection", value))), ")"))), "{", block))),
                new TypeRule("else", new LeftRule("else", new StripRule(new LeftRule("{", block))))
        )));

        var classMember = new OrRule(List.of(
                declaration,
                new TypeRule("method", new FirstRule(definitionHeader, "(", new FirstRule(new SplitMultipleRule(new ParamSplitter(), ", ", "params", new StripRule(definition)), ")", new StripRule(new LeftRule("{", block)))))
        ));
        var classChild = createBlock(classMember);

        var rootMember = new OrRule(List.of(
                new TypeRule("package", new LeftRule("package ", new RightRule(new ExtractNodeRule("internal", namespace), ";"))),
                new TypeRule("import", new LeftRule("import ", new RightRule(new ExtractNodeRule("external", namespace), ";"))),
                new TypeRule("class", new FirstRule(modifiers, "class ", new FirstRule(new StripRule(new ExtractStringRule("name")), "{", new RightRule(new ExtractNodeRule("child", classChild), "}"))))
        ));

        return createBlock(rootMember);
    }

    private static TypeRule createOperator(String name, String slice, Rule value) {
        return new TypeRule(name, new FirstRule(new StripRule(new ExtractNodeRule("left", value)), slice, new StripRule(new ExtractNodeRule("right", value))));
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
