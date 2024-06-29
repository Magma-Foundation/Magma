package magma.compile.rule;

import magma.api.collect.List;
import magma.api.collect.stream.Stream;
import magma.api.option.Option;

import java.util.function.Function;

/**
 * Interface representing a node in a tree structure used in compilation rules.
 * Provides methods to manipulate and query the node and its children.
 */
public interface Node {

    /**
     * Retrieves the type of the node.
     *
     * @return the type of the node as a String
     */
    String findType();

    /**
     * Formats the node with a specified depth, often for printing or debugging.
     *
     * @param depth the depth for formatting
     * @return a formatted String representation of the node
     */
    String formatWithDepth(int depth);

    /**
     * Formats the node with a specified depth.
     *
     * @param depth the depth for formatting
     * @return a formatted String representation of the node
     */
    String format(int depth);

    /**
     * Checks if the node is of the specified type.
     *
     * @param type the type to check against
     * @return true if the node is of the specified type, false otherwise
     */
    boolean is(String type);

    /**
     * Retypes the node to a new type.
     *
     * @param type the new type
     * @return a new Node with the specified type
     */
    Node retype(String type);

    /**
     * Adds a child node with a specified key.
     *
     * @param key   the key for the child node
     * @param value the child node to add
     * @return the updated Node
     */
    Node withNode(String key, Node value);

    /**
     * Adds a list of child nodes with a specified key.
     *
     * @param key    the key for the child nodes
     * @param values the list of child nodes to add
     * @return the updated Node
     */
    Node withNodeList(String key, List<Node> values);

    /**
     * Checks if the node has a child with the specified key.
     *
     * @param child the key of the child to check for
     * @return true if the node has the specified child, false otherwise
     */
    boolean has(String child);

    /**
     * Maps the list of child nodes associated with a specified key using a mapper function.
     *
     * @param key    the key for the child nodes
     * @param mapper the function to apply to the list of child nodes
     * @return the updated Node
     */
    Node mapNodes(String key, Function<List<Node>, List<Node>> mapper);

    /**
     * Finds a child node with a specified key.
     *
     * @param key the key of the child node to find
     * @return an Option containing the child node if present, or an empty Option if not present
     */
    Option<Node> findNode(String key);

    /**
     * Clears the node of all children of a specified type.
     *
     * @param type the type of children to clear
     * @return the updated Node
     */
    Node clear(String type);

    /**
     * Finds a list of child nodes with a specified key.
     *
     * @param key the key of the child nodes to find
     * @return an Option containing the list of child nodes if present, or an empty Option if not present
     */
    Option<List<Node>> findNodeList(String key);

    /**
     * Finds a string value associated with a specified key.
     *
     * @param key the key of the string value to find
     * @return an Option containing the string value if present, or an empty Option if not present
     */
    Option<String> findString(String key);

    /**
     * Finds a list of string values associated with a specified key.
     *
     * @param key the key of the string values to find
     * @return an Option containing the list of string values if present, or an empty Option if not present
     */
    Option<List<String>> findStringList(String key);

    /**
     * Returns a stream of the keys of the node's children.
     *
     * @return a Stream of the keys of the node's children
     */
    Stream<String> streamKeys();

    /**
     * Maps the list of strings associated with a specified key using a mapper function.
     *
     * @param key    the key for the strings
     * @param mapper the function to apply to the list of strings
     * @return the updated Node
     */
    Node mapStringList(String key, Function<List<String>, List<String>> mapper);

    /**
     * Adds a string with a specified key.
     *
     * @param key   the key for the string
     * @param value the string to add
     * @return the updated Node
     */
    Node withString(String key, String value);

    /**
     * Adds a string list with a specified key.
     *
     * @param key    the key for the string
     * @param values the string list to add
     * @return the updated Node
     */
    Node withStringList(String key, List<String> values);

    /**
     * Maps the node associated with a specified key using a mapper function.
     *
     * @param key    the key for the node
     * @param mapper the function to apply to the node
     * @return the updated Node
     */
    Node mapNode(String key, Function<Node, Node> mapper);

    /**
     * Removes a property.
     *
     * @param key The key.
     * @return Without the property.
     */
    Node remove(String key);
}
