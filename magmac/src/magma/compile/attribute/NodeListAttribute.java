package magma.compile.attribute;

import magma.compile.rule.Node;

import java.util.List;
import java.util.Optional;

public record NodeListAttribute(List<Node> nodeList) implements Attribute {
    @Override
    public Optional<List<Node>> asNodeList() {
        return Optional.of(nodeList);
    }
}
