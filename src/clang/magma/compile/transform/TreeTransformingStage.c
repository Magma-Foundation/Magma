#include "TreeTransformingStage.h"
int __lambda0__(){return transformNodes;
}
int __lambda1__(){return transformNodeLists;
}
int __lambda2__(){return transformNodeList;
}
int __lambda3__(){return transformer;
}
int __lambda4__(){return attachChildren;
}
int __lambda5__(){return transformNodes;
}
int __lambda6__(){return CompileError("Error when transforming", NodeContext(root), Lists.of(err));
}
public TreeTransformingStage(Transformer transformer){this.transformer = transformer;
}
Tuple<State, Node> attachChildren(Node node, String propertyKey, Tuple<State, List_<Node>> children){State newState = children.left();
        List_<Node> newChildren = children.right();
        Node withChildren = node.withNodeList(propertyKey, newChildren);return (newState, withChildren);
}
Result<Tuple<State, Node>, CompileError> transformNodes(State state, Node root){return root.streamNodes().foldToResult((state, root), __lambda0__(current.left(), current.right(), entry)).flatMapValue(__lambda1__(withNodes.left(), withNodes.right()));
}
Result<Tuple<State, Node>, CompileError> transformNodeLists(State state, Node root){return root.streamNodeLists().foldToResult((state, root), __lambda2__(current.left(), current.right(), tuple)).flatMapValue(__lambda3__.afterPass(withNodeLists.left(), withNodeLists.right()));
}
Result<Tuple<State, Node>, CompileError> transformNodes(State state, Node node, Tuple<String, Node> entry){return transform(state, entry.right()).mapValue(newChild -> {
            Node withChild = node.withNode(entry.left(), newChild.right());
            return new Tuple<>(newChild.left(), withChild);
        });
}
Result<Tuple<State, Node>, CompileError> transformNodeList(State state, Node node, Tuple<String, List_<Node>> entry){String propertyKey = entry.left();
        List_<Node> propertyValues = entry.right();return propertyValues.stream().foldToResult((state, Lists.empty()), this.transformElement).mapValue(__lambda4__(node, propertyKey, children));
}
Result<Tuple<State, List_<Node>>, CompileError> transformElement(Tuple<State, List_<Node>> current, Node element){State currentState = current.left();
        List_<Node> currentChildren = current.right();

        return transform(currentState, element).mapValue((Tuple<State, Node> newTuple) -> {
            State newState = newTuple.left();
            Node newChild = newTuple.right();
            return new Tuple<>(newState, currentChildren.add(newChild));
        });
}
Result<Tuple<State, Node>, CompileError> transform(State state, Node root){return transformer.beforePass(state, root).flatMapValue(__lambda5__(beforePass.left(), beforePass.right())).mapErr(__lambda6__);
}
