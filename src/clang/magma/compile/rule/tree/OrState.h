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
// expand None_ = None<struct >
// expand OrState_T = OrState<struct T>
// expand OrState_ = OrState<struct >
// expand Some_ = Some<struct >
// expand Result_T_List__CompileError = Result<struct T, struct List__CompileError>
// expand List__CompileError = List_<struct CompileError>
// expand Result_T_List__CompileError = Result<struct T, struct List__CompileError>
// expand List__CompileError = List_<struct CompileError>
// expand Err_ = Err<struct >
// expand OrState_T = OrState<struct T>
// expand OrState_ = OrState<struct >
// expand Option_T = Option<struct T>
// expand List__CompileError = List_<struct CompileError>
#endif

