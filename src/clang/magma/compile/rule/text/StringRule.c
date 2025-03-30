#include "StringRule.h"
Result<struct Node, struct CompileError> parse(struct String value}{return new Ok<>(new MapNode().withString(propertyKey(), value));}Result<struct String, struct CompileError> generate(struct Node input}{return input.findString(propertyKey)
                .<Result<String, CompileError>>map(Ok::new)
                .orElseGet(() -> new Err<>(new CompileError( + propertyKey + , new NodeContext(input))));}