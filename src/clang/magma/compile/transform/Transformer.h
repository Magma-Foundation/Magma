#ifndef magma_compile_transform_Transformer
#define magma_compile_transform_Transformer
#include "../../../magma/compile/CompileError.h"
#include "../../../magma/compile/Node.h"
#include "../../../magma/option/Tuple.h"
#include "../../../magma/result/Ok.h"
#include "../../../magma/result/Result.h"
struct Transformer{
};
// expand magma.result.Result<magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>, magma.compile.CompileError>
// expand magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>
// expand magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>
// expand magma.result.Ok<>
// expand magma.option.Tuple<>
// expand magma.result.Result<magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>, magma.compile.CompileError>
// expand magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>
// expand magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>
// expand magma.result.Ok<>
// expand magma.option.Tuple<>
magma.result.Result<magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>, magma.compile.CompileError> beforePass(magma.compile.transform.State state, magma.compile.Node node);
magma.result.Result<magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>, magma.compile.CompileError> afterPass(magma.compile.transform.State state, magma.compile.Node node);
#endif
