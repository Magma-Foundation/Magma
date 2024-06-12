package magma.compile.attribute;

import magma.api.Tuple;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * The Node interface provides methods to manipulate key-value pairs
 * where keys are strings and values are attributes. It supports
 * adding attributes, applying attributes, merging nodes, and
 * streaming entries.
 */
public interface Attributes {

    /**
     * Adds an attribute to the node.
     *
     * @param key the key to associate with the attribute
     * @param value the attribute to add
     * @return a new Node instance with the added attribute
     */
    Attributes with(String key, Attribute value);

    /**
     * Retrieves an attribute associated with the given key.
     *
     * @param key the key to lookup
     * @return an Optional containing the attribute if present, otherwise empty
     */
    Optional<Attribute> apply(String key);

    /**
     * Merges the current node with another node.
     *
     * @param other the node to merge with
     * @return a new Node instance representing the merged result
     */
    Attributes merge(Attributes other);

    /**
     * Streams the entries of the node as tuples of key and attribute.
     *
     * @return a Stream of tuples containing the keys and attributes
     */
    Stream<Tuple<String, Attribute>> streamEntries();

    Attributes mapValues(Function<Attribute, Attribute> mapper);
}