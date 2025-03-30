#include "StringRule.h"
// expand Result_Node_CompileError = Result<struct Node, struct CompileError>
// expand Result_String_CompileError = Result<struct String, struct CompileError>
struct Result_Node_CompileError parse(struct String value){return new Ok<>(new MapNode().withString(propertyKey(), value));}struct Result_String_CompileError generate(struct Node input){return input.findString(propertyKey)
                .<Result<String, CompileError>>map(Ok::new)
                .orElseGet(() -> new Err<>(new CompileError( + propertyKey + , new NodeContext(input))));}