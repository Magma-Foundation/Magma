#include "TreeTransformingStage.h"
auto __lambda0__();
auto __lambda1__();
auto __lambda2__();
auto __lambda3__();
auto __lambda4__();
auto __lambda5__();
auto __lambda6__();
magma.compile.transform.public TreeTransformingStage(magma.compile.transform.Transformer transformer){this.transformer = transformer;
}
magma.option.Tuple<magma.compile.transform.State, magma.compile.Node> attachChildren(magma.compile.Node node, String propertyKey, magma.option.Tuple<magma.compile.transform.State, magma.collect.list.List_<magma.compile.Node>> children){State newState = children.left();
        List_<Node> newChildren = children.right();
        Node withChildren = node.withNodeList(propertyKey, newChildren);return (newState, withChildren);
}
magma.result.Result<magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>, magma.compile.CompileError> transformNodes(magma.compile.transform.State state, magma.compile.Node root){return root.streamNodes().foldToResult((state, root), __lambda0__(current.left(), current.right(), entry)).flatMapValue(__lambda1__(withNodes.left(), withNodes.right()));
}
magma.result.Result<magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>, magma.compile.CompileError> transformNodeLists(magma.compile.transform.State state, magma.compile.Node root){return root.streamNodeLists().foldToResult((state, root), __lambda2__(current.left(), current.right(), tuple)).flatMapValue(__lambda3__.afterPass(withNodeLists.left(), withNodeLists.right()));
}
magma.result.Result<magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>, magma.compile.CompileError> transformNodes(magma.compile.transform.State state, magma.compile.Node node, magma.option.Tuple<String, magma.compile.Node> entry){return transform(state, entry.right()).mapValue(newChild -> {
            Node withChild = node.withNode(entry.left(), newChild.right());
            return new Tuple<>(newChild.left(), withChild);
        });
}
magma.result.Result<magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>, magma.compile.CompileError> transformNodeList(magma.compile.transform.State state, magma.compile.Node node, magma.option.Tuple<String, magma.collect.list.List_<magma.compile.Node>> entry){String propertyKey = entry.left();
        List_<Node> propertyValues = entry.right();return propertyValues.stream().foldToResult((state, Lists.empty()), this.transformElement).mapValue(__lambda4__(node, propertyKey, children));
}
magma.result.Result<magma.option.Tuple<magma.compile.transform.State, magma.collect.list.List_<magma.compile.Node>>, magma.compile.CompileError> transformElement(magma.option.Tuple<magma.compile.transform.State, magma.collect.list.List_<magma.compile.Node>> current, magma.compile.Node element){State currentState = current.left();
        List_<Node> currentChildren = current.right();

        return transform(currentState, element).mapValue((Tuple<State, Node> newTuple) -> {
            State newState = newTuple.left();
            Node newChild = newTuple.right();
            return new Tuple<>(newState, currentChildren.add(newChild));
        });
}
magma.result.Result<magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>, magma.compile.CompileError> transform(magma.compile.transform.State state, magma.compile.Node root){return transformer.beforePass(state, root).flatMapValue(__lambda5__(beforePass.left(), beforePass.right())).mapErr(__lambda6__);
}
