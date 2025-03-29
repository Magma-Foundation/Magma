package magma.compile;

import magma.collect.list.List_;
import magma.option.Option;

import java.util.function.Function;

public interface Node {
    Node withString(String propertyKey, String propertyValue);

    Option<String> findString(String propertyKey);

    Node withNodeList(String propertyKey, List_<Node> propertyValues);

    Option<List_<Node>> findNodeList(String propertyKey);

    String display();

    Node mapNodeList(String propertyKey, Function<List_<Node>, List_<Node>> mapper);

    boolean is(String type);
}
