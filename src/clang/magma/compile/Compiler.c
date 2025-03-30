#include "Compiler.h"
struct Result_Map__String_String_CompileError compile(struct String input, struct List__String namespace, struct String name){State state = new State(namespace, name);
        return JavaLang.createJavaRootRule().parse(input)
                .flatMapValue(tree -> new TreeTransformingStage(new ExpandGenerics()).transform(tree, state))
                .flatMapValue(tree -> {
                    Result<Node, CompileError> transform = new TreeTransformingStage(new FlattenGroup()).transform(tree, state);
                    return transform;
                })
                .flatMapValue(tree -> new TreeTransformingStage(new TransformAll()).transform(tree, state))
                .flatMapValue(tree -> new TreeTransformingStage(new FlattenGroup()).transform(tree, state))
                .flatMapValue(tree -> new TreeTransformingStage(new FlattenRoot()).transform(tree, state))
                .flatMapValue(tree -> new TreeTransformingStage(new Sorter()).transform(tree, state))
                .flatMapValue(Compiler::generateRoots);}struct Result_Map__String_String_CompileError generateRoots(struct Node roots){return roots.streamNodes().foldToResult(Maps.empty(), Compiler::generateTarget);}struct Result_Map__String_String_CompileError generateTarget(struct Map__String_String current, struct Tuple_String_Node tuple){String extension = tuple.left();
        Node root = tuple.right();
        return CLang.createCRootRule().generate(root).mapValue(generated -> current.with(extension, generated));}