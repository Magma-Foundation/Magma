package magma.compile.lang;

import magma.Main;
import magma.compile.rule.EmptyRule;
import magma.compile.rule.ExtractStringListRule;
import magma.compile.rule.ExtractStringRule;
import magma.compile.rule.LeftRule;
import magma.compile.rule.RightRule;
import magma.compile.rule.Rule;
import magma.compile.rule.SplitAtSliceRule;
import magma.compile.rule.StripRule;
import magma.compile.rule.TypeRule;

public class JavaLang {
    public static Rule createClassRule() {
        var modifiers = new ExtractStringListRule("modifiers", " ");
        var name = new StripRule(new ExtractStringRule("name"));
        var content = new ExtractStringRule("content");

        var splitAtSliceRule = new SplitAtSliceRule(new StripRule(modifiers), Main.CLASS_KEYWORD_WITH_SPACE, new StripRule(new SplitAtSliceRule(name, "{", new StripRule(new RightRule(content, "}")))));
        return new TypeRule("class", splitAtSliceRule);
    }

    public static Rule createPackageRule() {
        return new TypeRule("package", new LeftRule("package ", new ExtractStringListRule("namespace", ".")));
    }

    public static Rule createWhitespaceRule() {
        return new TypeRule("whitespace", new EmptyRule());
    }

    public static Rule createImportRule() {
        return new TypeRule("import", new LeftRule("import ", new ExtractStringRule("value")));
    }
}
