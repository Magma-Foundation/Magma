package magmac.app.lang.java;

public sealed interface JavaRootSegment permits JavaLang.Whitespace, JavaLang.Comment, JavaNamespacedNode, JavaLang.Structure {
}
