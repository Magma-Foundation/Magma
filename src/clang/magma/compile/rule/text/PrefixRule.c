#include "PrefixRule.h"
struct Result_Node_CompileError createPrefixErr(struct String input, struct String prefix}{return new Err<>(new CompileError( + prefix + , new StringContext(input)));}struct Result_Node_CompileError parse(struct String input}{if (!input.startsWith(prefix()))
            return createPrefixErr(input, prefix());

        String slice = input.substring(prefix().length());
        return childRule().parse(slice);}struct Result_String_CompileError generate(struct Node node}{return childRule.generate(node).mapValue(value -> prefix + value);}