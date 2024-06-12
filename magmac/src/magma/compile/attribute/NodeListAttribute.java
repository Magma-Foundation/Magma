package magma.compile.attribute;

import magma.compile.rule.Node;

import java.util.List;
import java.util.Optional;

public record NodeListAttribute(List<Node> nodeList) implements Attribute {
    public static final Factory<List<Node>> Factory = new Factory<List<Node>>() {
        @Override
        public Optional<List<Node>> fromAttribute(Attribute attribute) {
            return attribute.asNodeList();
        }

        @Override
        public Attribute toAttribute(List<Node> value) {
            return new NodeListAttribute(value);
        }
    };

    @Override
    public Optional<List<Node>> asNodeList() {
        return Optional.of(nodeList);
    }
}
