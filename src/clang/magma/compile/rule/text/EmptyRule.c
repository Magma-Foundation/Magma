#include "EmptyRule.h"
magma.result.Result<magma.compile.Node, magma.compile.CompileError> parse(String input){return input.isEmpty()?(MapNode()):(CompileError("Input not empty", StringContext(input)));
}
magma.result.Result<String, magma.compile.CompileError> generate(magma.compile.Node node){return ("");
}

