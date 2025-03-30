#include "EmptyRule.h"
struct Result_Node_CompileError parse(struct String input){
return input.isEmpty() ? new Ok<>(new MapNode()) : new Err<>(new CompileError(, new StringContext(input)));}
struct Result_String_CompileError generate(struct Node node){
return new Ok<>();}
