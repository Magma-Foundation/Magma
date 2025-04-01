#ifndef magma_compile_transform_TransformingStage
#define magma_compile_transform_TransformingStage
#include "../../../magma/compile/CompileError.h"
#include "../../../magma/compile/Node.h"
#include "../../../magma/option/Tuple.h"
#include "../../../magma/result/Result.h"
struct TransformingStage{
};
// expand Result<Tuple<State, Node>, CompileError>
// expand Tuple<State, Node>
// expand Tuple<State, Node>
Result<Tuple<State, Node>, CompileError> transform(State state, Node root);
#endif
