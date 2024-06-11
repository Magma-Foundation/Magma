package magma;

public class JavaLang {
    static SplitAtSliceRule createClassRule() {
        var modifiers = new ExtractStringListRule("modifiers", " ");
        var name = new StripRule(new ExtractStringRule("name"));
        var content = new ExtractStringRule("content");

        return new SplitAtSliceRule(new StripRule(modifiers), Main.CLASS_KEYWORD_WITH_SPACE, new StripRule(new SplitAtSliceRule(name, "{", new StripRule(new RightRule(content, "}")))));
    }
}
