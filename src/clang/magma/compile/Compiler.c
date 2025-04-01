#include "Compiler.h"
auto __lambda0__(){return (state, tree);
}
auto __lambda1__(){return TreeTransformingStage(transformer);
}
auto __lambda2__(){return current;
}
magma.result.Result<magma.collect.map.Map_<String, String>, magma.compile.CompileError> postLoad(magma.compile.transform.State state, magma.compile.Node tree){return TreeTransformingStage(FlattenStructs()).transform(state, tree).flatMapValue(transformUsing(FlattenGroup())).flatMapValue(transformUsing(Formatter())).flatMapValue(transformUsing(FlattenRoot())).flatMapValue(transformUsing(Sorter())).mapValue(Tuple.right).flatMapValue(Compiler.generateRoots);
}
magma.result.Result<magma.compile.Node, magma.compile.CompileError> preLoad(String input, magma.compile.transform.State state){return JavaLang.createJavaRootRule().parse(input).mapValue(__lambda0__).flatMapValue(transformUsing(ResolveTypes())).flatMapValue(transformUsing(TransformAll())).flatMapValue(transformUsing(FlattenGroup())).flatMapValue(transformUsing(ExpandGenerics())).flatMapValue(transformUsing(FlattenGroup())).flatMapValue(transformUsing(PruneTypeParameterized())).mapValue(Tuple.right);
}
magma.result.Result<magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>, magma.compile.CompileError>(*transformUsing)(magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>)(magma.compile.transform.Transformer transformer){return __lambda1__.transform(tree.left(), tree.right());
}
magma.result.Result<magma.collect.map.Map_<String, String>, magma.compile.CompileError> generateRoots(magma.compile.Node roots){return roots.streamNodes().foldToResult(Maps.empty(), Compiler.generateTarget);
}
magma.result.Result<magma.collect.map.Map_<String, String>, magma.compile.CompileError> generateTarget(magma.collect.map.Map_<String, String> current, magma.option.Tuple<String, magma.compile.Node> tuple){String extension = tuple.left();
        Node root = tuple.right();return CLang.createCRootRule().generate(root).mapValue(__lambda2__.with(extension, generated));
}
