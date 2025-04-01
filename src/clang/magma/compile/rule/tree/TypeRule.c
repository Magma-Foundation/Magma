#include "TypeRule.h"
auto __lambda0__(){return node;
}
auto __lambda1__(){return CompileError("Failed to parse of type '" + type + "'", StringContext(input), Lists.of(error));
}
magma.result.Result<magma.compile.Node, magma.compile.CompileError> parse(String input){return rule.parse(input).mapValue(__lambda0__.retype(type)).mapErr(__lambda1__);
}
magma.result.Result<String, magma.compile.CompileError> generate(magma.compile.Node node){if (node.is(type)) {
            return rule.generate(node).mapErr(err -> new CompileError("Failed to generate of type '" + type + "'", new NodeContext(node), Lists.of(err)));
        }return (CompileError("Node was not of type '" + type + "'", NodeContext(node)));
}
