package magmac.app.lang.node;

import magmac.api.collect.list.List;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.lang.JavaLang;

public record InvokableNode(JavaLang.Caller caller, List<JavaLang.Argument> arguments) implements Value, FunctionSegmentValue {
}
