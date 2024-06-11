package magma.compile.rule;

import magma.compile.Node;

import java.util.Optional;

/**
 * The Rule interface provides methods for converting between strings
 * and nodes. It includes methods to transform an input string into a node
 * and to transform a node back into a string.
 */
public interface Rule {

    /**
     * Converts an input string to a Node.
     *
     * @param input the input string to convert
     * @return an Optional containing the resulting Node if the conversion is successful, otherwise empty
     */
    Optional<Node> toNode(String input);

    /**
     * Converts a Node to a string representation.
     *
     * @param node the Node to convert
     * @return an Optional containing the resulting string if the conversion is successful, otherwise empty
     */
    Optional<String> fromNode(Node node);
}