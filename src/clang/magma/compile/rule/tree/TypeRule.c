#include "TypeRule.h"
struct Result_Node_CompileError parse(struct String input){return rule.parse(input).mapValue(__lambda0__).mapErr(__lambda1__);
}
struct Result_String_CompileError generate(struct Node node){if (node.is(type)) {
            return rule.generate(node).mapErr(err -> new CompileError("Failed to generate of type '" + type + "'", new NodeContext(node), Lists.of(err)));
        }return Err_(CompileError("Node was not of type '" + type + "'", NodeContext(node)));
}
auto __lambda0__();
auto __lambda1__();

