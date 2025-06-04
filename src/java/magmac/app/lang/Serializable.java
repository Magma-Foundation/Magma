package magmac.app.lang;

import magmac.app.compile.node.Node;

/**
 * Indicates that a value can be converted into a {@link Node} tree.
 */
public interface Serializable {
    /**
     * Serializes this value into its node representation.
     */
    Node serialize();
}
