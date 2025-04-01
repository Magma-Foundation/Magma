#include "PrefixRule.h"
int __lambda0__(){return prefix;
}
Result<Node, CompileError> createPrefixErr(String input, String prefix){return (CompileError("Prefix '" + prefix + "' not present", StringContext(input)));
}
Result<Node, CompileError> parse(String input){if (!input.startsWith(prefix()))
            return createPrefixErr(input, prefix());

        String slice = input.substring(prefix().length());return childRule().parse(slice);
}
Result<String, CompileError> generate(Node node){return childRule.generate(node).mapValue(__lambda0__+value);
}
