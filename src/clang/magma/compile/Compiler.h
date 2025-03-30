#ifndef magma_compile_Compiler
#define magma_compile_Compiler
#include "../../windows/collect/map/Maps.h"
#include "../../magma/collect/list/List_.h"
#include "../../magma/collect/map/Map_.h"
#include "../../magma/compile/lang/CLang.h"
#include "../../magma/compile/lang/FlattenRoot.h"
#include "../../magma/compile/lang/Formatter.h"
#include "../../magma/compile/lang/JavaLang.h"
#include "../../magma/compile/lang/Sorter.h"
#include "../../magma/compile/lang/TransformAll.h"
#include "../../magma/compile/transform/FlattenGroup.h"
#include "../../magma/compile/transform/State.h"
#include "../../magma/compile/transform/TreeTransformingStage.h"
#include "../../magma/option/Tuple.h"
#include "../../magma/result/Result.h"
struct Compiler{
};
// expand Result_Map__String_String_CompileError = Result<struct Map__String_String, struct CompileError>
// expand Map__String_String = Map_<struct String, struct String>
// expand List__String = List_<struct String>
// expand Result_Map__String_String_CompileError = Result<struct Map__String_String, struct CompileError>
// expand Map__String_String = Map_<struct String, struct String>
// expand Result_Map__String_String_CompileError = Result<struct Map__String_String, struct CompileError>
// expand Map__String_String = Map_<struct String, struct String>
// expand Map__String_String = Map_<struct String, struct String>
// expand Tuple_String_Node = Tuple<struct String, struct Node>
struct Result_Map__String_String_CompileError compile(struct String input, struct List__String namespace, struct String name);
struct Result_Map__String_String_CompileError generateRoots(struct Node roots);
struct Result_Map__String_String_CompileError generateTarget(struct Map__String_String current, struct Tuple_String_Node tuple);
auto __lambda0__();
auto __lambda1__();
auto __lambda2__();
auto __lambda3__();
auto __lambda4__();
auto __lambda5__();
auto __lambda6__();
auto __lambda7__();
#endif

