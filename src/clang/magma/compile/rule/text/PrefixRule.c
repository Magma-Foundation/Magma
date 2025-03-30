#include "PrefixRule.h"
Result<struct Node, struct CompileError> createPrefixErr(struct String input, struct String prefix}{return new Err<>(new CompileError( + prefix + , new StringContext(input)));}Result<struct Node, struct CompileError> parse(struct String input}{if (!input.startsWith(prefix()))
            return createPrefixErr(input, prefix());

        String slice = input.substring(prefix().length());
        return childRule().parse(slice);}Result<struct String, struct CompileError> generate(struct Node node}{return childRule.generate(node).mapValue(value -> prefix + value);}