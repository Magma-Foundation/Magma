#include "NodeRule.h"
Result<struct Node, struct CompileError> parse(struct String input}{return childRule.parse(input)
                .mapValue(node -> new MapNode().withNode(propertyKey, node))
                .mapErr(err -> new CompileError( + propertyKey + , new StringContext(input), Lists.of(err)));}Result<struct String, struct CompileError> generate(struct Node node}{return node.findNode(propertyKey)
                .map(childRule::generate)
                .orElseGet(() -> new Err<>(new CompileError( + propertyKey + , new NodeContext(node))));}