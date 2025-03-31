#ifndef magma_compile_transform_TransformingStage
#define magma_compile_transform_TransformingStage
#include "../../../magma/compile/CompileError.h"
#include "../../../magma/compile/Node.h"
#include "../../../magma/option/Tuple.h"
#include "../../../magma/result/Result.h"
struct TransformingStage{
};
// expand magma.result.Result<magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>, magma.compile.CompileError>
// expand magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>
// expand magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>
magma.result.Result<magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>, magma.compile.CompileError> transform(magma.compile.transform.State state, magma.compile.Node root);
#endif

