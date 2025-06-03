package magmac.app.lang.java;

public sealed interface JavaFunctionSegmentValue permits JavaLang.Assignment, JavaBreak, JavaContinue, JavaLang.Invokable, JavaPost, JavaLang.Return, JavaYieldNode {
}
