package magmac.app.compile.node;

import magmac.api.Option;
import magmac.api.Tuple2;
import magmac.api.collect.list.List;
import magmac.api.iter.Iter;
import magmac.app.compile.error.CompileResult;

import java.util.function.Function;

public interface Node {
    CompileResult<NodeList> findNodeListOrError(String key);

    CompileResult<Node> findNodeOrError(String key);

    Iter<Tuple2<String, Node>> iterNodes();

    String display();

    String format(int depth);

    Iter<Tuple2<String, NodeList>> iterNodeLists();

    Node withString(String key, String value);

    Option<String> findString(String key);

    Node merge(Node other);

    boolean is(String type);

    Node retype(String type);

    Node withNodeList(String key, NodeList values);

    Option<NodeList> findNodeList(String key);

    Node withNode(String key, Node value);

    Option<Node> findNode(String key);

    Iter<Tuple2<String, String>> iterStrings();

    boolean hasNodeList(String key);

    CompileResult<Tuple2<Node, NodeList>> removeNodeListOrError(String key);

    Option<Tuple2<Node, NodeList>> removeNodeList(String key);

    boolean isEmpty();

    <T> Node withNodeListSerialized(String key, List<T> list, Function<T, Node> serializer);

    CompileResult<Tuple2<Node, String>> removeString(String key);

    CompileResult<Tuple2<Node, Node>> removeNode(String key);
}
