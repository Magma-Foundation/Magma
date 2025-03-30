#include "StringRule.h"
struct Result_Node_CompileError parse(struct String value){return Ok_(MapNode().withString(propertyKey(), value));
}
struct Result_String_CompileError generate(struct Node input){return input.findString(propertyKey).map(Ok.new).orElseGet(__lambda0__);
}
auto __lambda0__();

