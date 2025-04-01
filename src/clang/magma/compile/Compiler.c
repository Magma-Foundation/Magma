#include "Compiler.h"
int __lambda0__(){return (state, tree);
}
int __lambda1__(){return TreeTransformingStage(transformer);
}
int __lambda2__(){return current;
}
Result<Map_<String, String>, CompileError> postLoad(State state, Node tree){return TreeTransformingStage(FlattenStructs()).transform(state, tree).flatMapValue(transformUsing(FlattenGroup())).flatMapValue(transformUsing(Compacter())).flatMapValue(transformUsing(Formatter())).flatMapValue(transformUsing(FlattenRoot())).flatMapValue(transformUsing(Sorter())).mapValue(Tuple.right).flatMapValue(Compiler.generateRoots);
}
Result<Node, CompileError> preLoad(String input, State state){return JavaLang.createJavaRootRule().parse(input).mapValue(__lambda0__).flatMapValue(transformUsing(ResolveTypes())).flatMapValue(transformUsing(TransformAll())).flatMapValue(transformUsing(FlattenGroup())).flatMapValue(transformUsing(ExpandGenerics())).flatMapValue(transformUsing(FlattenGroup())).flatMapValue(transformUsing(PruneTypeParameterized())).mapValue(Tuple.right);
}
Result<Tuple<State, Node>, CompileError>(*transformUsing)(Tuple<State, Node>)(Transformer transformer){return __lambda1__.transform(tree.left(), tree.right());
}
Result<Map_<String, String>, CompileError> generateRoots(Node roots){return roots.streamNodes().foldToResult(Maps.empty(), Compiler.generateTarget);
}
Result<Map_<String, String>, CompileError> generateTarget(Map_<String, String> current, Tuple<String, Node> tuple){String extension = tuple.left();
        Node root = tuple.right();return CLang.createCRootRule().generate(root).mapValue(__lambda2__.with(extension, generated));
}
