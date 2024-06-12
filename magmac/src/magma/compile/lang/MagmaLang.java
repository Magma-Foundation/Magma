package magma.compile.lang;

import magma.compile.rule.MembersRule;
import magma.compile.rule.OrRule;
import magma.compile.rule.TypeRule;
import magma.compile.rule.text.extract.ExtractStringListRule;
import magma.compile.rule.text.extract.ExtractStringRule;
import magma.compile.rule.text.RightRule;
import magma.compile.rule.result.SplitAtSliceRule;

import java.util.List;

public class MagmaLang {
    public static SplitAtSliceRule createFunctionRule() {
        var modifiers = new ExtractStringListRule("modifiers", " ");
        var name = new ExtractStringRule("name");
        var content = new ExtractStringRule("content");

        return new SplitAtSliceRule(modifiers, " ", new SplitAtSliceRule(name, "() => {\n\t", new RightRule(content, "}")));
    }

    public static TypeRule createRootRule() {
        return new TypeRule("root", new MembersRule("children", new OrRule(List.of(
                new RightRule(JavaLang.createImportRule(), "\n"),
                new TypeRule("function", createFunctionRule())
        ))));
    }
}
