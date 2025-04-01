#ifndef magma_compile_Compiler
#define magma_compile_Compiler
#include "../../windows/collect/map/Maps.h"
#include "../../magma/collect/map/Map_.h"
#include "../../magma/compile/lang/CLang.h"
#include "../../magma/compile/lang/Compacter.h"
#include "../../magma/compile/lang/ExpandGenerics.h"
#include "../../magma/compile/lang/FlattenRoot.h"
#include "../../magma/compile/lang/FlattenStructs.h"
#include "../../magma/compile/lang/Formatter.h"
#include "../../magma/compile/lang/JavaLang.h"
#include "../../magma/compile/lang/PruneTypeParameterized.h"
#include "../../magma/compile/lang/ResolveTypes.h"
#include "../../magma/compile/lang/Sorter.h"
#include "../../magma/compile/lang/TransformAll.h"
#include "../../magma/compile/transform/FlattenGroup.h"
#include "../../magma/compile/transform/State.h"
#include "../../magma/compile/transform/Transformer.h"
#include "../../magma/compile/transform/TreeTransformingStage.h"
#include "../../magma/option/Tuple.h"
#include "../../magma/result/Result.h"
struct Compiler{
};
// expand Result<Map_<String, String>, CompileError>
// expand Map_<String, String>
// expand Map_<String, String>
// expand Result<Node, CompileError>
// expand Result<Tuple<State, Node>, CompileError>
// expand Tuple<State, Node>
// expand Tuple<State, Node>
// expand Tuple<State, Node>
// expand Result<Map_<String, String>, CompileError>
// expand Map_<String, String>
// expand Map_<String, String>
// expand Result<Map_<String, String>, CompileError>
// expand Map_<String, String>
// expand Map_<String, String>
// expand Map_<String, String>
// expand Tuple<String, Node>
// expand Tuple<>
int __lambda0__();
int __lambda1__();
int __lambda2__();
Result<Map_<String, String>, CompileError> postLoad(State state, Node tree);
Result<Node, CompileError> preLoad(String input, State state);
Result<Tuple<State, Node>, CompileError>(*transformUsing)(Tuple<State, Node>)(Transformer transformer);
Result<Map_<String, String>, CompileError> generateRoots(Node roots);
Result<Map_<String, String>, CompileError> generateTarget(Map_<String, String> current, Tuple<String, Node> tuple);
#endif
