package magma;

public class MagmaLang {
    static SplitAtSliceRule createFunctionRule() {
        var modifiers = new ExtractStringListRule("modifiers", " ");
        var name = new ExtractStringRule("name");
        var content = new ExtractStringRule("content");

        return new SplitAtSliceRule(modifiers, " ", new SplitAtSliceRule(name, "() => {\n\t", new RightRule(content, "}")));
    }
}
