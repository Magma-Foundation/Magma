package magma.compile.rule;

import magma.api.option.Option;
import magma.compile.attribute.Attribute;
import magma.compile.attribute.Attributes;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface Node {
    String findType();

    Node withString(String key, String value);

    default Node with(String key, Attribute value) {
        return mapAttributes(attributes -> attributes.with(key, value));
    }

    String formatWithDepth(int depth);

    String format(int depth);

    boolean is(String type);

    Node mapAttributes(Function<Attributes, Attributes> mapper);

    Node retype(String type);

    Node withAttributes(Attributes attributes);

    Node withNode(String key, Node value);

    Node withNodeList(String key, List<Node> values);

    boolean has(String child);

    Node mapNodes(String key, Function<List<Node>, List<Node>> mapper);

    Optional<Node> findNode(String key);

    Node clear(String type);

    Optional<List<Node>> findNodeList(String key);

    Option<String> findString(String key);
}
