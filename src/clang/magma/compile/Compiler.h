#ifndef magma_compile_Compiler
#define magma_compile_Compiler
#include "../../windows/collect/map/Maps.h"
#include "../../magma/collect/list/List_.h"
#include "../../magma/collect/map/Map_.h"
#include "../../magma/compile/lang/CLang.h"
#include "../../magma/compile/lang/FlattenRoot.h"
#include "../../magma/compile/lang/JavaLang.h"
#include "../../magma/compile/lang/Sorter.h"
#include "../../magma/compile/lang/TransformAll.h"
#include "../../magma/compile/transform/FlattenGroup.h"
#include "../../magma/compile/transform/State.h"
#include "../../magma/compile/transform/TreeTransformingStage.h"
#include "../../magma/option/Tuple.h"
#include "../../magma/result/Result.h"
struct Compiler{};
Result<Map_<struct String, struct String>, struct CompileError> compile(struct String input, List_<struct String> namespace, struct String name);
Result<Map_<struct String, struct String>, struct CompileError> generateRoots(struct Node roots);
Result<Map_<struct String, struct String>, struct CompileError> generateTarget(Map_<struct String, struct String> current, Tuple<struct String, struct Node> tuple);
#endif
