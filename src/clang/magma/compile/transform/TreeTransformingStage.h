#ifndef magma_compile_transform_TreeTransformingStage
#define magma_compile_transform_TreeTransformingStage
#include "../../../windows/collect/list/Lists.h"
#include "../../../magma/collect/list/List_.h"
#include "../../../magma/compile/CompileError.h"
#include "../../../magma/compile/Node.h"
#include "../../../magma/option/Tuple.h"
#include "../../../magma/result/Result.h"
struct TreeTransformingStage{struct Transformer transformer};
struct public TreeTransformingStage(struct Transformer transformer);
Result<struct Node, struct CompileError> transform(struct Node root, struct State state);
Result<struct Node, struct CompileError> transformNodeLists(struct Node withNodes, struct State state);
Result<struct Node, struct CompileError> mapNodes(struct Node node, Tuple<struct String, struct Node> tuple, struct State state);
Result<struct Node, struct CompileError> mapNodeList(struct Node node, Tuple<struct String, List_<struct Node>> tuple, struct State state);
Result<List_<struct Node>, struct CompileError> mapNodeListElement(List_<struct Node> elements, struct Node element, struct State state);
#endif
