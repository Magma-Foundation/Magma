package com.meti.node;

import com.meti.api.option.None;
import com.meti.api.option.Option;

import java.util.List;
import java.util.Optional;

/**
 * Represents a property on a node within the AST.
 */
public interface Attribute {
    /**
     * This property as a list of strings, if possible.
     * @return The list.
     */
    default Option<List<String>> asListOfStrings() {
        return new None<>();
    }

    /**
     * This property as a list of nodes, if possible.
     * @return The list.
     */
    default Option<List<MapNode>> asListOfNodes() {
        return new None<>();
    }

    /**
     * This property as a string, if possible.
     * @return The string.
     */
    default Optional<String> asString() {
        return Optional.empty();
    }

    /**
     * This property as a node, if possible.
     * @return The node.
     */
    default Option<MapNode> asNode() {
        return new None<>();
    }

    /**
     * Determines whether this attribute is of the provided type.
     *
     * @param type The type.
     * @return The result.
     */
    default boolean is(String type) {
        return false;
    }
}
