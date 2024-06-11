package magma.compile.lang;

import magma.Main;
import magma.compile.rule.ExtractStringListRule;
import magma.compile.rule.ExtractStringRule;
import magma.compile.rule.RightRule;
import magma.compile.rule.SplitAtSliceRule;
import magma.compile.rule.StripRule;

public class JavaLang {
    public static SplitAtSliceRule createClassRule() {
        var modifiers = new ExtractStringListRule("modifiers", " ");
        var name = new StripRule(new ExtractStringRule("name"));
        var content = new ExtractStringRule("content");

        return new SplitAtSliceRule(new StripRule(modifiers), Main.CLASS_KEYWORD_WITH_SPACE, new StripRule(new SplitAtSliceRule(name, "{", new StripRule(new RightRule(content, "}")))));
    }
}
