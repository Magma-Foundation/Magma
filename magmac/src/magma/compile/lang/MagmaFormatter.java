package magma.compile.lang;

import magma.compile.rule.Node;

public class MagmaFormatter extends Modifier {
    @Override
    protected Node postVisit(Node node) {
        if (node.is("import")) {
            return node.withString("right-indent", "\n");
        }

        if(node.is("declaration")) {
            return node.withString("left-indent", "\n\t");
        }

        if(node.is("function")) {
            return node.withString("left-indent", "\n\t");
        }

        return node;
    }
}
