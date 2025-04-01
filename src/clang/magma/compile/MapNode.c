#include "MapNode.h"
int __lambda0__(){return withNodeList;
}
int __lambda1__(){return value;
}
int __lambda2__(){return node;
}
int __lambda3__(){return withNode;
}
public MapNode(){this((), Maps.empty(), Maps.empty(), Maps.empty());
}
public MapNode(Option<String> maybeType, Map_<String, String> strings, Map_<String, Node> nodes, Map_<String, List_<Node>> nodeLists){this.maybeType = maybeType;
        this.strings = strings;
        this.nodes = nodes;
        this.nodeLists = nodeLists;
}
public MapNode(String type){this((type), Maps.empty(), Maps.empty(), Maps.empty());
}
String formatEntry(int depth, String key, String value){String format = "%s%s: %s";
        String indent = "\t".repeat(depth + 1);return format.formatted(indent, key, value);
}
Node withString(String propertyKey, String propertyValue){return MapNode(maybeType, strings.with(propertyKey, propertyValue), nodes, nodeLists);
}
Option<String> findString(String propertyKey){return strings.find(propertyKey);
}
Node withNodeList(String propertyKey, List_<Node> propertyValues){return MapNode(maybeType, strings, nodes, nodeLists.with(propertyKey, propertyValues));
}
Option<List_<Node>> findNodeList(String propertyKey){return nodeLists.find(propertyKey);
}
String display(){return format(0);
}
String format(int depth){String typeString = maybeType.map(type -> type + " ").orElse("");

        Option<String> joinedStrings = strings.stream()
                .map(entry -> formatEntry(depth, entry.left(), "\"" + entry.right() + "\""))
                .collect(new Joiner(",\n"));

        Option<String> joinedNodes = nodes.stream()
                .map(entry -> formatEntry(depth, entry.left(), entry.right().format(depth + 1)))
                .collect(new Joiner(",\n"));

        Option<String> joinedNodeLists = nodeLists.stream()
                .map(entry -> formatEntry(depth, entry.left(), formatList(entry, depth)))
                .collect(new Joiner(",\n"));

        String joined = Streams.of(joinedStrings, joinedNodes, joinedNodeLists)
                .flatMap(Streams::fromOption)
                .collect(new Joiner(",\n"))
                .orElse("");return typeString+"{\n" + joined + "\n" +
                "\t".repeat(depth) +
                "}";
}
String formatList(Tuple<String, List_<Node>> entry, int depth){return "[" + entry.right()
                .stream()
                .map(node -> node.format(depth + 1))
                .collect(new Joiner(", "))
                .orElse("") + "]";
}
Node mapNodeList(String propertyKey, List_<Node>(*mapper)(List_<Node>)){return findNodeList(propertyKey).map(mapper).map(__lambda0__(propertyKey, nodeList)).orElse(this);
}
int is(String type){return this.maybeType.filter(__lambda1__.equals(type)).isPresent();
}
Node retype(String type){return MapNode((type), strings, nodes, nodeLists);
}
Node merge(Node other){Node withStrings = other.streamStrings().<Node>foldWithInitial(this, (node, tuple) -> node.withString(tuple.left(), tuple.right()));
        Node withNodes = other.streamNodes().foldWithInitial(withStrings, (node, tuple) -> node.withNode(tuple.left(), tuple.right()));return other.streamNodeLists().foldWithInitial(withNodes, __lambda2__.withNodeList(tuple.left(), tuple.right()));
}
Stream<Tuple<String, String>> streamStrings(){return strings.stream();
}
Stream<Tuple<String, List_<Node>>> streamNodeLists(){return nodeLists.stream();
}
Node withNode(String propertyKey, Node propertyValue){return MapNode(maybeType, strings, nodes.with(propertyKey, propertyValue), nodeLists);
}
Option<Node> findNode(String propertyKey){return nodes.find(propertyKey);
}
Stream<Tuple<String, Node>> streamNodes(){return nodes.stream();
}
Node mapNode(String propertyKey, Node(*mapper)(Node)){return findNode(propertyKey).map(mapper).map(__lambda3__(propertyKey, node)).orElse(this);
}
Node withNodeLists(Map_<String, List_<Node>> nodeLists){return MapNode(maybeType, strings, nodes, this.nodeLists.withAll(nodeLists));
}
Node withNodes(Map_<String, Node> nodes){return MapNode(maybeType, strings, this.nodes.withAll(nodes), this.nodeLists);
}
Node removeNode(String propertyKey){return MapNode(maybeType, strings, nodes.remove(propertyKey), nodeLists);
}
int hasNode(String propertyKey){return nodes.containsKey(propertyKey);
}
int hasNodeList(String propertyKey){return nodeLists.containsKey(propertyKey);
}
int equalsTo(Node other){boolean isType = maybeType.map(other::is).orElse(false);

        Map_<String, String> stringsCopy = other.streamStrings().collect(new MapCollector<>());
        Map_<String, Node> nodesCopy = other.streamNodes().collect(new MapCollector<>());
        Map_<String, List_<Node>> nodeListCopy = other.streamNodeLists().collect(new MapCollector<>());return isType&&Maps.equalsTo(strings, stringsCopy, String.equals)&&Maps.equalsTo(nodes, nodesCopy, Node.equalsTo)&&Maps.equalsTo(nodeLists, nodeListCopy, this.doNodeListsEqual);
}
int doNodeListsEqual(List_<Node> nodeList, List_<Node> nodeList2){return Lists.equalsTo(nodeList, nodeList2, Node.equalsTo);
}
String toString(){return display();
}
