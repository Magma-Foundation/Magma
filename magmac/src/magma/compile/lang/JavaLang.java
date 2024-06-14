package magma.compile.lang;

import magma.compile.rule.LazyRule;
import magma.compile.rule.OrRule;
import magma.compile.rule.Rule;
import magma.compile.rule.TypeRule;
import magma.compile.rule.split.FirstRule;
import magma.compile.rule.split.LastRule;
import magma.compile.rule.split.MembersSplitter;
import magma.compile.rule.split.SplitMultipleRule;
import magma.compile.rule.text.LeftRule;
import magma.compile.rule.text.RightRule;
import magma.compile.rule.text.StripRule;
import magma.compile.rule.text.extract.ExtractNodeRule;
import magma.compile.rule.text.extract.ExtractStringRule;
import magma.compile.rule.text.extract.SimpleExtractStringListRule;

import java.util.List;

public class JavaLang {
    public static Rule createRootRule() {
        var namespace = new TypeRule("namespace", new SimpleExtractStringListRule("namespace", "\\."));
        var modifiers = new StripRule(new SimpleExtractStringListRule("modifiers", " "));

        var value = new LazyRule();
        value.setRule(new OrRule(List.of(
                new TypeRule("string", new LeftRule("\"", new RightRule(new ExtractStringRule("value"), "\""))),
                new TypeRule("invocation", new FirstRule(new ExtractNodeRule("caller", value), "(", new RightRule(new ExtractStringRule("arguments"), ")"))),
                new TypeRule("access", new LastRule(new ExtractNodeRule("parent", value), ".", new ExtractStringRule("child"))),
                new TypeRule("reference", new ExtractStringRule("value"))
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
}
