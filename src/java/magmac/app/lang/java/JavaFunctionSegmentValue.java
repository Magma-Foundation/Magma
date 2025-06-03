package magmac.app.lang.java;

public sealed interface JavaFunctionSegmentValue permits JavaAssignmentNode, JavaBreak, JavaContinue, JavaInvokable, JavaPost, JavaReturnNode, JavaYieldNode {
}
