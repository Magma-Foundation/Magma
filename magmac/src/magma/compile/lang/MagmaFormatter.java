package magma.compile.lang;

import magma.compile.rule.Node;

public class MagmaFormatter extends Modifier {
    @Override
    protected Node postVisit(Node node) {
        if (node.is("import")) {
            return node.withString("right-indent", "\n");
        }

        return node;
    }
}
