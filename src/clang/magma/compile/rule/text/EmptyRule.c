#include "EmptyRule.h"
struct Result_Node_CompileError parse(struct String input){return input.isEmpty()?Ok_(MapNode()):Err_(CompileError("Input not empty", StringContext(input)));
}
struct Result_String_CompileError generate(struct Node node){return Ok_("");
}

