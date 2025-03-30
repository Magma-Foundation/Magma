#include "MapNode.h"
struct public MapNode(){this(None_(), Maps.empty(), Maps.empty(), Maps.empty());
}
struct public MapNode(struct Option_String maybeType, struct Map__String_String strings, struct Map__String_Node nodes, struct Map__String_List__Node nodeLists){this.maybeType = maybeType;
        this.strings = strings;
        this.nodes = nodes;
        this.nodeLists = nodeLists;
}
struct public MapNode(struct String type){this(Some_(type), Maps.empty(), Maps.empty(), Maps.empty());
}
struct String formatEntry(int depth, struct String key, struct String value){String format = "%s%s: %s";
        String indent = "\t".repeat(depth + 1);return format.formatted(indent, key, value);
}
struct Node withString(struct String propertyKey, struct String propertyValue){return MapNode(maybeType, strings.with(propertyKey, propertyValue), nodes, nodeLists);
}
struct Option_String findString(struct String propertyKey){return strings.find(propertyKey);
}
struct Node withNodeList(struct String propertyKey, struct List__Node propertyValues){return MapNode(maybeType, strings, nodes, nodeLists.with(propertyKey, propertyValues));
}
struct Option_List__Node findNodeList(struct String propertyKey){return nodeLists.find(propertyKey);
}
struct String display(){return format(0);
}
struct String format(int depth){String typeString = maybeType.map(type -> type + " ").orElse("");

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
struct String formatList(struct Tuple_String_List__Node entry, int depth){return "[" + entry.right()
                .stream()
                .map(node -> node.format(depth + 1))
                .collect(new Joiner(", "))
                .orElse("") + "]";
}
struct Node mapNodeList(struct String propertyKey, struct List__Node(*mapper)(struct List__Node)){return findNodeList(propertyKey).map(mapper).map(__lambda0__).orElse(this);
}
int is(struct String type){return this.maybeType.filter(__lambda1__).isPresent();
}
struct Node retype(struct String type){return MapNode(Some_(type), strings, nodes, nodeLists);
}
struct Node merge(struct Node other){Node withStrings = other.streamStrings().<Node>foldWithInitial(this, (node, tuple) -> node.withString(tuple.left(), tuple.right()));
        Node withNodes = other.streamNodes().foldWithInitial(withStrings, (node, tuple) -> node.withNode(tuple.left(), tuple.right()));return other.streamNodeLists().foldWithInitial(withNodes, __lambda2__);
}
struct Stream_Tuple_String_String streamStrings(){return strings.stream();
}
struct Stream_Tuple_String_List__Node streamNodeLists(){return nodeLists.stream();
}
struct Node withNode(struct String propertyKey, struct Node propertyValue){return MapNode(maybeType, strings, nodes.with(propertyKey, propertyValue), nodeLists);
}
struct Option_Node findNode(struct String propertyKey){return nodes.find(propertyKey);
}
struct Stream_Tuple_String_Node streamNodes(){return nodes.stream();
}
struct Node mapNode(struct String propertyKey, struct Node(*mapper)(struct Node)){return findNode(propertyKey).map(mapper).map(__lambda3__).orElse(this);
}
struct Node withNodeLists(struct Map__String_List__Node nodeLists){return MapNode(maybeType, strings, nodes, this.nodeLists.withAll(nodeLists));
}
struct Node withNodes(struct Map__String_Node nodes){return MapNode(maybeType, strings, this.nodes.withAll(nodes), this.nodeLists);
}
struct Node removeNode(struct String propertyKey){return MapNode(maybeType, strings, nodes.remove(propertyKey), nodeLists);
}
int hasNode(struct String propertyKey){return nodes.containsKey(propertyKey);
}
int hasNodeList(struct String propertyKey){return nodeLists.containsKey(propertyKey);
}
auto __lambda0__();
auto __lambda1__();
auto __lambda2__();
auto __lambda3__();

