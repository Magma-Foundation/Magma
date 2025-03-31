#ifndef magma_compile_Compiler
#define magma_compile_Compiler
#include "../../windows/collect/map/Maps.h"
#include "../../magma/collect/map/Map_.h"
#include "../../magma/compile/lang/CLang.h"
#include "../../magma/compile/lang/ExpandGenerics.h"
#include "../../magma/compile/lang/FlattenRoot.h"
#include "../../magma/compile/lang/Formatter.h"
#include "../../magma/compile/lang/JavaLang.h"
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
// expand magma.result.Result<magma.collect.map.Map_<String, String>, magma.compile.CompileError>
// expand magma.collect.map.Map_<String, String>
// expand magma.collect.map.Map_<String, String>
// expand magma.result.Result<magma.compile.Node, magma.compile.CompileError>
// expand magma.result.Result<magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>, magma.compile.CompileError>
// expand magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>
// expand magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>
// expand magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>
// expand magma.result.Result<magma.collect.map.Map_<String, String>, magma.compile.CompileError>
// expand magma.collect.map.Map_<String, String>
// expand magma.collect.map.Map_<String, String>
// expand magma.result.Result<magma.collect.map.Map_<String, String>, magma.compile.CompileError>
// expand magma.collect.map.Map_<String, String>
// expand magma.collect.map.Map_<String, String>
// expand magma.collect.map.Map_<String, String>
// expand magma.option.Tuple<String, magma.compile.Node>
magma.result.Result<magma.collect.map.Map_<String, String>, magma.compile.CompileError> postLoad(magma.compile.transform.State state, magma.compile.Node tree);
magma.result.Result<magma.compile.Node, magma.compile.CompileError> preLoad(String input, magma.compile.transform.State state);
magma.result.Result<magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>, magma.compile.CompileError>(*transformUsing)(magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>)(magma.compile.transform.Transformer transformer);
magma.result.Result<magma.collect.map.Map_<String, String>, magma.compile.CompileError> generateRoots(magma.compile.Node roots);
magma.result.Result<magma.collect.map.Map_<String, String>, magma.compile.CompileError> generateTarget(magma.collect.map.Map_<String, String> current, magma.option.Tuple<String, magma.compile.Node> tuple);
auto __lambda0__();
auto __lambda1__();
auto __lambda2__();
#endif

