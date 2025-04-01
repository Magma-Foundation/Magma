#include "NodeRule.h"
int __lambda0__(){return MapNode();
}
int __lambda1__(){return CompileError("Failed to attach node '" + propertyKey + "'", StringContext(input), Lists.of(err));
}
int __lambda2__(){return (CompileError("Node '" + propertyKey + "' not present", NodeContext(node)));
}
Result<Node, CompileError> parse(String input){return childRule.parse(input).mapValue(__lambda0__.withNode(propertyKey, node)).mapErr(__lambda1__);
}
Result<String, CompileError> generate(Node node){return node.findNode(propertyKey).map(childRule.generate).orElseGet(__lambda2__);
}
