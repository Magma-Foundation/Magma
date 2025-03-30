#include "Rule.h"
Result<struct Node, struct CompileError> parse(struct String input);
Result<struct String, struct CompileError> generate(struct Node node);
