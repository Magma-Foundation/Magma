#include "TypeRule.h"
int __lambda0__(){return node;
}
int __lambda1__(){return CompileError("Failed to parse of type '" + type + "'", StringContext(input), Lists.of(error));
}
Result<Node, CompileError> parse(String input){return rule.parse(input).mapValue(__lambda0__.retype(type)).mapErr(__lambda1__);
}
Result<String, CompileError> generate(Node node){if (node.is(type)) {
            return rule.generate(node).mapErr(err -> new CompileError("Failed to generate of type '" + type + "'", new NodeContext(node), Lists.of(err)));
        }return (CompileError("Node was not of type '" + type + "'", NodeContext(node)));
}
