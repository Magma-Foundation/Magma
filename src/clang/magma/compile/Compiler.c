#include "Compiler.h"
struct Result_Map__String_String_CompileError compile(struct String input, struct List__String namespace, struct String name);
struct Result_Map__String_String_CompileError generateRoots(struct Node roots);
struct Result_Map__String_String_CompileError generateTarget(struct Map__String_String current, struct Tuple_String_Node tuple);
