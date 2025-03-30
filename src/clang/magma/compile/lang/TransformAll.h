#ifndef magma_compile_lang_TransformAll
#define magma_compile_lang_TransformAll
#include "../../../windows/collect/list/JavaList.h"
#include "../../../windows/collect/list/Lists.h"
#include "../../../windows/collect/stream/Streams.h"
#include "../../../magma/collect/list/ListCollector.h"
#include "../../../magma/collect/list/List_.h"
#include "../../../magma/collect/stream/Joiner.h"
#include "../../../magma/compile/CompileError.h"
#include "../../../magma/compile/MapNode.h"
#include "../../../magma/compile/Node.h"
#include "../../../magma/compile/context/NodeContext.h"
#include "../../../magma/compile/transform/State.h"
#include "../../../magma/compile/transform/Transformer.h"
#include "../../../magma/option/Tuple.h"
#include "../../../magma/result/Err.h"
#include "../../../magma/result/Ok.h"
#include "../../../magma/result/Result.h"
struct TransformAll{};
Tuple<List_<struct Node>, List_<struct Node>> bucketClassMember(Tuple<List_<struct Node>, List_<struct Node>> tuple, struct Node element);
Result<struct Node, struct CompileError> find(struct Node node, struct String propertyKey);
Result<List_<struct Node>, struct CompileError> findNodeList(struct Node value, struct String propertyKey);
int isFunctionalImport(struct Node child);
Result<struct Node, struct CompileError> afterPass(struct State state, struct Node node);
struct String stringify(struct Node node);
Result<struct Node, struct CompileError> beforePass(struct State state, struct Node node);
int hasTypeParams(struct Node child);
#endif
