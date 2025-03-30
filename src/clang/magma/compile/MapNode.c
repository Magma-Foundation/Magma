#include "MapNode.h"
struct public MapNode(}{this(new None<>(), Maps.empty(), Maps.empty(), Maps.empty());}struct public MapNode(Option<struct String> maybeType, Map_<struct String, struct String> strings, Map_<struct String, struct Node> nodes, Map_<struct String, List_<struct Node>> nodeLists}{this.maybeType = maybeType;
        this.strings = strings;
        this.nodes = nodes;
        this.nodeLists = nodeLists;}struct public MapNode(struct String type}{this(new Some<>(type), Maps.empty(), Maps.empty(), Maps.empty());}struct String formatEntry(struct int depth, struct String key, struct String value}{return .repeat(depth + 1) + key +  + value;}struct Node withString(struct String propertyKey, struct String propertyValue}{return new MapNode(maybeType, strings.with(propertyKey, propertyValue), nodes, nodeLists);}Option<struct String> findString(struct String propertyKey}{return strings.find(propertyKey);}struct Node withNodeList(struct String propertyKey, List_<struct Node> propertyValues}{return new MapNode(maybeType, strings, nodes, nodeLists.with(propertyKey, propertyValues));}Option<List_<struct Node>> findNodeList(struct String propertyKey}{return nodeLists.find(propertyKey);}struct String display(}{return format(0);}struct String format(struct int depth}{String typeString = maybeType.map(type -> type + ).orElse();

        Option<String> joinedStrings = strings.stream()
                .map(entry -> formatEntry(depth, entry.left(),  + entry.right() + ))
                .collect(new Joiner());

        Option<String> joinedNodes = nodes.stream()
                .map(entry -> formatEntry(depth, entry.left(), entry.right().format(depth + 1)))
                .collect(new Joiner());

        Option<String> joinedNodeLists = nodeLists.stream()
                .map(entry -> formatEntry(depth, entry.left(), formatList(entry, depth)))
                .collect(new Joiner());

        String joined = Streams.of(joinedStrings, joinedNodes, joinedNodeLists)
                .flatMap(Streams::fromOption)
                .collect(new Joiner())
                .orElse();return typeString +  + joined +  +
                .repeat(depth) +
                ;}struct String formatList(Tuple<struct String, List_<struct Node>> entry, struct int depth}{return  + entry.right()
                .stream()
                .map(node -> node.format(depth + 1))
                .collect(new Joiner())
                .orElse() + ;}struct Node mapNodeList(struct String propertyKey, List_<struct Node>(*mapper)(List_<struct Node>)}{return findNodeList(propertyKey)
                .map(mapper)
                .map(nodeList -> withNodeList(propertyKey, nodeList))
                .orElse(this);}int is(struct String type}{return this.maybeType.filter(value -> value.equals(type)).isPresent();}struct Node retype(struct String type}{return new MapNode(new Some<>(type), strings, nodes, nodeLists);}struct Node merge(struct Node other}{Node withStrings = other.streamStrings().<Node>foldWithInitial(this, (node, tuple) -> node.withString(tuple.left(), tuple.right()));
        Node withNodes = other.streamNodes().foldWithInitial(withStrings, (node, tuple) -> node.withNode(tuple.left(), tuple.right()));
        return other.streamNodeLists().foldWithInitial(withNodes, (node, tuple) -> node.withNodeList(tuple.left(), tuple.right()));}Stream<Tuple<struct String, struct String>> streamStrings(}{return strings.stream();}Stream<Tuple<struct String, List_<struct Node>>> streamNodeLists(}{return nodeLists.stream();}struct Node withNode(struct String propertyKey, struct Node propertyValue}{return new MapNode(maybeType, strings, nodes.with(propertyKey, propertyValue), nodeLists);}Option<struct Node> findNode(struct String propertyKey}{return nodes.find(propertyKey);}Stream<Tuple<struct String, struct Node>> streamNodes(}{return nodes.stream();}struct Node mapNode(struct String propertyKey, struct Node(*mapper)(struct Node)}{return findNode(propertyKey).map(mapper).map(node -> withNode(propertyKey, node)).orElse(this);}struct Node withNodeLists(Map_<struct String, List_<struct Node>> nodeLists}{return new MapNode(maybeType, strings, nodes, this.nodeLists.withAll(nodeLists));}struct Node withNodes(Map_<struct String, struct Node> nodes}{return new MapNode(maybeType, strings, this.nodes.withAll(nodes), this.nodeLists);}struct Node removeNode(struct String propertyKey}{return new MapNode(maybeType, strings, nodes.remove(propertyKey), nodeLists);}int hasNode(struct String propertyKey}{return nodes.containsKey(propertyKey);}