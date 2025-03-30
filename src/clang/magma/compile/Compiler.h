#ifndef magma_compile_Compiler
#define magma_compile_Compiler
#include "../../windows/collect/map/Maps.h"
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
// expand Result_Node_CompileError = Result<struct Node, struct CompileError>
// expand Result_Map__String_String_CompileError = Result<struct Map__String_String, struct CompileError>
// expand Map__String_String = Map_<struct String, struct String>
// expand Result_Map__String_String_CompileError = Result<struct Map__String_String, struct CompileError>
// expand Map__String_String = Map_<struct String, struct String>
// expand Map__String_String = Map_<struct String, struct String>
// expand Tuple_String_Node = Tuple<struct String, struct Node>
struct Result_Map__String_String_CompileError postLoad(struct State state, struct Node tree);
struct Result_Node_CompileError preLoad(struct String input, struct State state);
struct Result_Map__String_String_CompileError generateRoots(struct Node roots);
struct Result_Map__String_String_CompileError generateTarget(struct Map__String_String current, struct Tuple_String_Node tuple);
auto __lambda0__();
auto __lambda1__();
auto __lambda2__();
auto __lambda3__();
auto __lambda4__();
auto __lambda5__();
auto __lambda6__();
#endif

