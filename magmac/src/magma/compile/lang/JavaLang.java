package magma.compile.lang;

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

        var value = new OrRule(List.of(
                new TypeRule("string", new LeftRule("\"", new RightRule(new ExtractStringRule("value"), "\""))),
                new TypeRule("any", new ExtractStringRule("value"))
        ));

        var type = new OrRule(List.of(
                new TypeRule("symbol", new ExtractStringRule("value"))
        ));

        var definition = new LastRule(new LastRule(new SimpleExtractStringListRule("modifiers", " "), " ", new ExtractNodeRule("type", type)), " ", new ExtractStringRule("name"));
        var classMember = new OrRule(List.of(
                new TypeRule("declaration", new FirstRule(new StripRule(definition), "=", new RightRule(new StripRule(new ExtractNodeRule("value", value)), ";"))),
                new TypeRule("method", new FirstRule(definition, "(", new ExtractStringRule("right"))),
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
