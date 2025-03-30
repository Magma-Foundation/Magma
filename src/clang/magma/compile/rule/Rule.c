#include "Rule.h"
// expand Result_Node_CompileError = Result<struct Node, struct CompileError>
// expand Result_String_CompileError = Result<struct String, struct CompileError>
struct Result_Node_CompileError parse(struct String input);
struct Result_String_CompileError generate(struct Node node);
