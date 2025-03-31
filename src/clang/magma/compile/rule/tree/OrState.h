#ifndef magma_compile_rule_tree_OrState
#define magma_compile_rule_tree_OrState
#include "../../../../windows/collect/list/Lists.h"
#include "../../../../magma/collect/list/List_.h"
#include "../../../../magma/compile/CompileError.h"
#include "../../../../magma/option/None.h"
#include "../../../../magma/option/Option.h"
#include "../../../../magma/option/Some.h"
#include "../../../../magma/result/Err.h"
#include "../../../../magma/result/Ok.h"
#include "../../../../magma/result/Result.h"
magma.compile.rule.tree.public OrState();
magma.compile.rule.tree.OrState<T> withValue(T value);
magma.result.Result<T, magma.collect.list.List_<magma.compile.CompileError>> toResult();
magma.compile.rule.tree.OrState<T> withError(magma.compile.CompileError error);
auto __lambda0__();
// expand magma.option.Option<T>
// expand magma.collect.list.List_<magma.compile.CompileError>
// expand magma.option.None<>
// expand magma.compile.rule.tree.OrState<T>
// expand magma.compile.rule.tree.OrState<>
// expand magma.option.Some<>
// expand magma.result.Result<T, magma.collect.list.List_<magma.compile.CompileError>>
// expand magma.collect.list.List_<magma.compile.CompileError>
// expand magma.collect.list.List_<magma.compile.CompileError>
// expand magma.result.Result<T, magma.collect.list.List_<magma.compile.CompileError>>
// expand magma.collect.list.List_<magma.compile.CompileError>
// expand magma.collect.list.List_<magma.compile.CompileError>
// expand magma.compile.rule.tree.OrState<T>
// expand magma.compile.rule.tree.OrState<>
#endif

