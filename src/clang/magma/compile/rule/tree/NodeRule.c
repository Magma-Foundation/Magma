#include "NodeRule.h"
int __lambda0__(){return MapNode();
}
int __lambda1__(){return CompileError("Failed to attach node '" + propertyKey + "'", StringContext(input), Lists.of(err));
}
int __lambda2__(){return (CompileError("Node '" + propertyKey + "' not present", NodeContext(node)));
}
magma.result.Result<magma.compile.Node, magma.compile.CompileError> parse(String input){return childRule.parse(input).mapValue(__lambda0__.withNode(propertyKey, node)).mapErr(__lambda1__);
}
magma.result.Result<String, magma.compile.CompileError> generate(magma.compile.Node node){return node.findNode(propertyKey).map(childRule.generate).orElseGet(__lambda2__);
}
