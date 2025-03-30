#ifndef magma_compile_lang_Sorter
#define magma_compile_lang_Sorter
#include "../../../windows/collect/list/Lists.h"
#include "../../../magma/collect/list/List_.h"
#include "../../../magma/collect/stream/Joiner.h"
#include "../../../magma/compile/CompileError.h"
#include "../../../magma/compile/MapNode.h"
#include "../../../magma/compile/Node.h"
#include "../../../magma/compile/transform/State.h"
#include "../../../magma/compile/transform/Transformer.h"
#include "../../../magma/option/Tuple.h"
#include "../../../magma/result/Ok.h"
#include "../../../magma/result/Result.h"
struct Sorter{};
struct Node asRoot(struct List__Node left);
struct Result_Node_CompileError afterPass(struct State state, struct Node node);
struct Result_Node_CompileError beforePass(struct State state, struct Node node);
#endif
