#include "SuffixRule.h"
int __lambda0__(){return result;
}
magma.result.Result<magma.compile.Node, magma.compile.CompileError> parse(String input){if (!input.endsWith(suffix()))
            return new Err<>(new CompileError("Suffix '" + suffix() + "' not present", new StringContext(input)));

        String slice = input.substring(0, input.length() - suffix.length());return childRule.parse(slice);
}
magma.result.Result<String, magma.compile.CompileError> generate(magma.compile.Node node){return childRule.generate(node).mapValue(__lambda0__+suffix);
}
