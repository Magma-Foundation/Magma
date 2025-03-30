#include "PrefixRule.h"
struct Result_Node_CompileError createPrefixErr(struct String input, struct String prefix){return Err_(CompileError("Prefix '" + prefix + "' not present", StringContext(input)));
}
struct Result_Node_CompileError parse(struct String input){if (!input.startsWith(prefix()))
            return createPrefixErr(input, prefix());

        String slice = input.substring(prefix().length());return childRule().parse(slice);
}
struct Result_String_CompileError generate(struct Node node){return childRule.generate(node).mapValue(__lambda0__);
}
auto __lambda0__();

