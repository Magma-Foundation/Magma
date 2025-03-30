#include "TypeRule.h"
struct Result_Node_CompileError parse(struct String input}{return rule.parse(input)
                .mapValue(node -> node.retype(type))
                .mapErr(error -> new CompileError( + type + , new StringContext(input), Lists.of(error)));}struct Result_String_CompileError generate(struct Node node}{if (node.is(type)) {
            return rule.generate(node);
        }

        return new Err<>(new CompileError( + type + , new NodeContext(node)));}