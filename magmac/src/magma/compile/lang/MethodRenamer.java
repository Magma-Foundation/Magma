package magma.compile.lang;

import magma.compile.rule.Node;

public class MethodRenamer extends Modifier {
    @Override
    protected Node postVisit(Node node) {
        if(node.is("method")) return node.retype("function");
        return node;
    }
}
