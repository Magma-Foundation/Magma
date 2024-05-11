package com.meti.rule;

import com.meti.node.MapNode;

/**
 * Indicates that a property could not be found on a node.
 */
public class NoSuchPropertyException extends RuleException {
    public NoSuchPropertyException(String propertyName, MapNode node) {
        super("No property of name '%s' found on node: %s".formatted(propertyName, node));
    }
}
