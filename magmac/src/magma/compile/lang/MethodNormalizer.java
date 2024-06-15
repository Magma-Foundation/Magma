package magma.compile.lang;

import magma.compile.rule.Node;

public class MethodNormalizer extends Generator {
    @Override
    protected Node postVisit(Node node) {
        if(!node.is("method")) {
            return node;
        }

        return node.retype("function");
    }
}
