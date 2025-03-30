#include "StripRule.h"
// expand Result_Node_CompileError = Result<struct Node, struct CompileError>
// expand Result_String_CompileError = Result<struct String, struct CompileError>
struct Result_Node_CompileError parse(struct String input){return childRule().parse(input.strip());}struct Result_String_CompileError generate(struct Node node){return childRule.generate(node);}