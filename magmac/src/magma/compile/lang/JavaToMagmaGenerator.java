package magma.compile.lang;

import magma.compile.rule.Node;

public class JavaToMagmaGenerator extends Generator {
    @Override
    protected Node preVisit(Node node) {
        return node;
    }

    @Override
    protected Node postVisit(Node node) {
        return node;
    }
}