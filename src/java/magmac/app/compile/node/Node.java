package magmac.app.compile.node;

import magmac.api.Tuple2;
import magmac.api.collect.Iter;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface Node {
    Iter<Tuple2<String, List<Node>>> iterNodeLists();

    Node withString(String key, String value);

    Optional<String> findString(String key);

    Node merge(Node other);

    Map<String, String> strings();

    boolean is(String type);

    Node retype(String type);

    Node withNodeList(String key, List<Node> values);

    Optional<List<Node>> findNodeList(String key);

    Node withNode(String key, Node value);

    Optional<Node> findNode(String key);
}
