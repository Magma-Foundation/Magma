#include "PrefixRule.h"
auto __lambda0__();
magma.result.Result<magma.compile.Node, magma.compile.CompileError> createPrefixErr(String input, String prefix){return (CompileError("Prefix '" + prefix + "' not present", StringContext(input)));
}
magma.result.Result<magma.compile.Node, magma.compile.CompileError> parse(String input){if (!input.startsWith(prefix()))
            return createPrefixErr(input, prefix());

        String slice = input.substring(prefix().length());return childRule().parse(slice);
}
magma.result.Result<String, magma.compile.CompileError> generate(magma.compile.Node node){return childRule.generate(node).mapValue(__lambda0__+value);
}
