#include "NodeRule.h"
// expand Result_Node_CompileError = Result<struct Node, struct CompileError>
// expand Result_String_CompileError = Result<struct String, struct CompileError>
struct Result_Node_CompileError parse(struct String input){return childRule.parse(input)
                .mapValue(node -> new MapNode().withNode(propertyKey, node))
                .mapErr(err -> new CompileError( + propertyKey + , new StringContext(input), Lists.of(err)));}struct Result_String_CompileError generate(struct Node node){return node.findNode(propertyKey)
                .map(childRule::generate)
                .orElseGet(() -> new Err<>(new CompileError( + propertyKey + , new NodeContext(node))));}