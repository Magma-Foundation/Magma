package magma.compile.rule;

import magma.compile.Node;

import java.util.Optional;

public interface Rule {
    Optional<Node> toNode(String input);

    Optional<String> fromNode(Node node);
}
