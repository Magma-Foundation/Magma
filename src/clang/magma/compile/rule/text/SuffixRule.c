#include "SuffixRule.h"
// expand Result_Node_CompileError = Result<struct Node, struct CompileError>
// expand Result_String_CompileError = Result<struct String, struct CompileError>
struct Result_Node_CompileError parse(struct String input){if (!input.endsWith(suffix()))
            return new Err<>(new CompileError( + suffix() + , new StringContext(input)));

        String slice = input.substring(0, input.length() - suffix.length());
        return childRule.parse(slice);}struct Result_String_CompileError generate(struct Node node){return childRule.generate(node).mapValue(result -> result + suffix);}