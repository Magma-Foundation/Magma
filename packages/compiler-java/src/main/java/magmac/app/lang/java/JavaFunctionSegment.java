package magmac.app.lang.java;

public sealed interface JavaFunctionSegment permits JavaLang.Case, JavaLang.FunctionStatement, JavaLang.Block, JavaLang.Return, JavaLang.Whitespace, JavaLang.Comment {
}
