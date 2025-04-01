#ifndef magma_compile_lang_Transformers
#define magma_compile_lang_Transformers
#include "../../../magma/collect/list/List_.h"
#include "../../../magma/compile/CompileError.h"
#include "../../../magma/compile/Node.h"
#include "../../../magma/compile/context/NodeContext.h"
#include "../../../magma/result/Err.h"
#include "../../../magma/result/Ok.h"
#include "../../../magma/result/Result.h"
struct Transformers{
};
// expand Result<Node, CompileError>
// expand Result<Node, CompileError>
// expand Result<List_<Node>, CompileError>
// expand List_<Node>
// expand List_<Node>
// expand Result<List_<Node>, CompileError>
// expand List_<Node>
// expand List_<Node>
// expand Err<>
// expand Err<>
int __lambda0__();
int __lambda1__();
Result<Node, CompileError> findNode(Node node, String propertyKey);
Result<List_<Node>, CompileError> findNodeList(Node value, String propertyKey);
#endif
