#include "NodeListRule.h"
int __lambda0__(){return childRule;
}
int __lambda1__(){return MapNode();
}
int __lambda2__(){return CompileError("Failed to attach node list '" + propertyKey + "'", StringContext(input), Lists.of(err));
}
int __lambda3__(){return (CompileError("Node list '" + propertyKey + "' not present", NodeContext(node)));
}
int __lambda4__(){return CompileError("Failed to generate node list '" + propertyKey + "'", NodeContext(node), Lists.of(err));
}
int __lambda5__(){return childRule;
}
int __lambda6__(){return divider;
}
Result<Node, CompileError> parse(String input){List_<String> segments = divider.divide(input);if (segments.isEmpty()) return new Ok<>(new MapNode());return segments.stream().foldToResult(Lists.empty(), __lambda0__().parse(element).mapValue(children.add)).mapValue(__lambda1__.withNodeList(propertyKey(), children)).mapErr(__lambda2__);
}
Result<String, CompileError> generate(Node node){return node.findNodeList(propertyKey).map(this.generateChildren).orElseGet(__lambda3__).mapErr(__lambda4__);
}
Result<String, CompileError> generateChildren(List_<Node> children){return children.stream().foldToResult("", __lambda5__.generate(element).mapValue(__lambda6__.join(current, result)));
}
