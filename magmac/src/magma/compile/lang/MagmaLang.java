package magma.compile.lang;

import magma.compile.rule.MembersRule;
import magma.compile.rule.OrRule;
import magma.compile.rule.Rule;
import magma.compile.rule.TypeRule;
import magma.compile.rule.text.LeftRule;
import magma.compile.rule.text.extract.ExtractStringListRule;
import magma.compile.rule.text.extract.ExtractStringRule;
import magma.compile.rule.text.RightRule;
import magma.compile.rule.result.SplitAtSliceRule;

import java.util.List;

public class MagmaLang {
    public static SplitAtSliceRule createFunctionRule() {
        var modifiers = new ExtractStringListRule("modifiers", " ");
        var name = new ExtractStringRule("name");
        var content = new MembersRule("content", createStatementRule());

        return new SplitAtSliceRule(modifiers, " ", new SplitAtSliceRule(name, "() => {\n\t", new RightRule(content, "}")));
    }

    private static Rule createStatementRule() {
        return new OrRule(List.of(
                new TypeRule("any", new ExtractStringRule("content"))
        ));
    }

    public static TypeRule createRootRule() {
        return new TypeRule("root", new MembersRule("children", new OrRule(List.of(
                new RightRule(new TypeRule("import", new LeftRule("import ", new ExtractStringRule("value"))), "\n"),
                new TypeRule("function", createFunctionRule())
        ))));
    }
}
