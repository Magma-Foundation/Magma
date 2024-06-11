package magma.compile.lang;

import magma.compile.rule.ExtractStringListRule;
import magma.compile.rule.ExtractStringRule;
import magma.compile.rule.RightRule;
import magma.compile.rule.SplitAtSliceRule;

public class MagmaLang {
    public static SplitAtSliceRule createFunctionRule() {
        var modifiers = new ExtractStringListRule("modifiers", " ");
        var name = new ExtractStringRule("name");
        var content = new ExtractStringRule("content");

        return new SplitAtSliceRule(modifiers, " ", new SplitAtSliceRule(name, "() => {\n\t", new RightRule(content, "}")));
    }
}
