#include "SuffixRule.h"
Result<struct Node, struct CompileError> parse(struct String input}{if (!input.endsWith(suffix()))
            return new Err<>(new CompileError( + suffix() + , new StringContext(input)));

        String slice = input.substring(0, input.length() - suffix.length());
        return childRule.parse(slice);}Result<struct String, struct CompileError> generate(struct Node node}{return childRule.generate(node).mapValue(result -> result + suffix);}