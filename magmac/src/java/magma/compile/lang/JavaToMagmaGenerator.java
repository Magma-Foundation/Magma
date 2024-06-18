package magma.compile.lang;

import magma.api.Tuple;
import magma.compile.rule.Node;

public class JavaToMagmaGenerator extends Generator {
    private static Node removePackagesFromBlock(Node node) {
        if (!node.is("block")) return node;

        return node.mapNodes("children", children -> children.stream()
                .filter(child -> !child.is("package"))
                .toList());
    }

    @Override
    protected Tuple<Node, Integer> preVisit(Node node, int depth) {
        var newNode = removePackagesFromBlock(node);
        return new Tuple<>(newNode, depth);
    }
}
