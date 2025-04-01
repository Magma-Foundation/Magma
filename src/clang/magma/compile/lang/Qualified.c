#include "Qualified.h"
int __lambda0__(){return segment;
}
Node to(List_<String> list){List_<Node> resolved = list.stream()
                .map(segment -> new MapNode().withString("value", segment))
                .collect(new ListCollector<Node>());return MapNode("qualified").withNodeList("segments", resolved);
}
List_<String> from(Node node){return node.findNodeList("segments").orElse(Lists.empty()).stream().map(__lambda0__.findString("value")).flatMap(Streams.fromOption).collect(());
}
