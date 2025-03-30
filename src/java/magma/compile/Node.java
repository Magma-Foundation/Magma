package magma.compile;

import magma.collect.list.List_;
import magma.collect.stream.Stream;
import magma.option.Option;
import magma.option.Tuple;

import java.util.function.Function;

public interface Node {
    Node withString(String propertyKey, String propertyValue);

    Option<String> findString(String propertyKey);

    Node withNodeList(String propertyKey, List_<Node> propertyValues);

    Option<List_<Node>> findNodeList(String propertyKey);

    String display();

    Node mapNodeList(String propertyKey, Function<List_<Node>, List_<Node>> mapper);

    boolean is(String type);

    Node retype(String type);

    Node merge(Node other);

    Stream<Tuple<String, String>> streamStrings();

    Stream<Tuple<String, List_<Node>>> streamNodeLists();

    Node withNode(String propertyKey, Node propertyValue);

    Option<Node> findNode(String propertyKey);

    Stream<Tuple<String, Node>> streamNodes();
}
