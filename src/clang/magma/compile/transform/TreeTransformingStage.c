#include "TreeTransformingStage.h"
struct public TreeTransformingStage(struct Transformer transformer){this.transformer = transformer;
}
struct Result_Node_CompileError transform(struct Node root, struct State state){return transformer.beforePass(state, root).flatMapValue(__lambda0__);
}
struct Result_Node_CompileError transformNodes(struct Node root, struct State state){return root.streamNodes().foldToResult(root, __lambda1__).flatMapValue(__lambda2__);
}
struct Result_Node_CompileError transformNodeLists(struct Node root, struct State state){return root.streamNodeLists().foldToResult(root, __lambda3__).flatMapValue(__lambda4__);
}
struct Result_Node_CompileError mapNodes(struct Node node, struct Tuple_String_Node tuple, struct State state){return transform(tuple.right(), state).mapValue(__lambda5__);
}
struct Result_Node_CompileError mapNodeList(struct Node node, struct Tuple_String_List__Node tuple, struct State state){return tuple.right().stream().foldToResult(Lists.empty(), __lambda6__).mapValue(__lambda7__);
}
struct Result_List__Node_CompileError mapNodeListElement(struct List__Node elements, struct Node element, struct State state){return transform(element, state).mapValue(elements.add);
}
auto __lambda0__();
auto __lambda1__();
auto __lambda2__();
auto __lambda3__();
auto __lambda4__();
auto __lambda5__();
auto __lambda6__();
auto __lambda7__();

