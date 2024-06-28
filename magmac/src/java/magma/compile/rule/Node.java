package magma.compile.rule;

import magma.api.collect.stream.Stream;
import magma.api.option.Option;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface Node {
    String findType();

    String formatWithDepth(int depth);

    String format(int depth);

    boolean is(String type);

    Node retype(String type);

    Node withNode(String key, Node value);

    Node withNodeList(String key, List<Node> values);

    boolean has(String child);

    Node mapNodes(String key, Function<List<Node>, List<Node>> mapper);

    Optional<Node> findNode(String key);

    Node clear(String type);

    Optional<List<Node>> findNodeList(String key);

    Option<String> findString(String key);

    Option<magma.api.collect.List<String>> findStringList(String key);

    Stream<String> streamKeys();
}
