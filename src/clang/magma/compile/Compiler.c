#include "Compiler.h"
struct Result_Map__String_String_CompileError postLoad(struct State state, struct Node tree){return TreeTransformingStage(Formatter()).transform(tree, state).flatMapValue(Compiler.generateRoots);
}
struct Result_Node_CompileError preLoad(struct String input, struct State state){return JavaLang.createJavaRootRule().parse(input).flatMapValue(__lambda0__).flatMapValue(__lambda1__).flatMapValue(__lambda2__).flatMapValue(__lambda3__).flatMapValue(__lambda4__).flatMapValue(__lambda5__);
}
struct Result_Map__String_String_CompileError generateRoots(struct Node roots){return roots.streamNodes().foldToResult(Maps.empty(), Compiler.generateTarget);
}
struct Result_Map__String_String_CompileError generateTarget(struct Map__String_String current, struct Tuple_String_Node tuple){String extension = tuple.left();
        Node root = tuple.right();return CLang.createCRootRule().generate(root).mapValue(__lambda6__);
}
auto __lambda0__();
auto __lambda1__();
auto __lambda2__();
auto __lambda3__();
auto __lambda4__();
auto __lambda5__();
auto __lambda6__();

