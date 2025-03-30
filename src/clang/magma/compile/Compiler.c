#include "Compiler.h"
Result<Map_<struct String, struct String>, struct CompileError> compile(struct String input, List_<struct String> namespace, struct String name}{State state = new State(namespace, name);
        return JavaLang.createJavaRootRule().parse(input)
                .flatMapValue(tree -> new TreeTransformingStage(new TransformAll()).transform(tree, state))
                .flatMapValue(tree -> new TreeTransformingStage(new FlattenGroup()).transform(tree, state))
                .flatMapValue(tree -> new TreeTransformingStage(new FlattenRoot()).transform(tree, state))
                .flatMapValue(tree -> new TreeTransformingStage(new Sorter()).transform(tree, state))
                .flatMapValue(Compiler::generateRoots);}Result<Map_<struct String, struct String>, struct CompileError> generateRoots(struct Node roots}{return roots.streamNodes().foldToResult(Maps.empty(), Compiler::generateTarget);}Result<Map_<struct String, struct String>, struct CompileError> generateTarget(Map_<struct String, struct String> current, Tuple<struct String, struct Node> tuple}{String extension = tuple.left();
        Node root = tuple.right();
        return CLang.createCRootRule().generate(root).mapValue(generated -> current.with(extension, generated));}