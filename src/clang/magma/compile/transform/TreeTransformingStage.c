#include "TreeTransformingStage.h"
struct public TreeTransformingStage(struct Transformer transformer){this.transformer = transformer;
}
struct Result_Node_CompileError transform(struct Node root, struct State state){return transformer.beforePass(state, root)
                .flatMapValue(beforePass -> transformNodes(beforePass, state));
}
struct Result_Node_CompileError transformNodes(struct Node root, struct State state){return root.streamNodes()
                .foldToResult(root, (node, tuple) -> mapNodes(node, tuple, state))
                .flatMapValue(withNodes -> transformNodeLists(withNodes, state));
}
struct Result_Node_CompileError transformNodeLists(struct Node root, struct State state){return root.streamNodeLists()
                .foldToResult(root, (node, tuple) -> mapNodeList(node, tuple, state))
                .flatMapValue(node1 -> transformer.afterPass(state, node1));
}
struct Result_Node_CompileError mapNodes(struct Node node, struct Tuple_String_Node tuple, struct State state){return transform(tuple.right(), state).mapValue(newChild -> node.withNode(tuple.left(), newChild));
}
struct Result_Node_CompileError mapNodeList(struct Node node, struct Tuple_String_List__Node tuple, struct State state){return tuple.right()
                .stream()
                .foldToResult(Lists.<Node>empty(), (current, element) -> mapNodeListElement(current, element, state))
                .mapValue(children -> node.withNodeList(tuple.left(), children));
}
struct Result_List__Node_CompileError mapNodeListElement(struct List__Node elements, struct Node element, struct State state){return transform(element, state).mapValue(elements::add);
}

