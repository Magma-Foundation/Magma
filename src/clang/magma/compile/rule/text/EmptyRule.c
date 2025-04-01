#include "EmptyRule.h"
Result<Node, CompileError> parse(String input){return input.isEmpty()?(MapNode()):(CompileError("Input not empty", StringContext(input)));
}
Result<String, CompileError> generate(Node node){return ("");
}
