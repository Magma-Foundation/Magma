#ifndef magma_compile_CompileError
#define magma_compile_CompileError
#include "../../windows/collect/list/Lists.h"
#include "../../magma/collect/list/List_.h"
#include "../../magma/collect/stream/Joiner.h"
#include "../../magma/collect/stream/head/HeadedStream.h"
#include "../../magma/collect/stream/head/RangeHead.h"
#include "../../magma/compile/context/Context.h"
#include "../../magma/error/Error.h"
struct CompileError{struct String message;struct Context context;struct List__CompileError errors;
};
// expand List__CompileError = List_<struct CompileError>
// expand List__CompileError = List_<struct CompileError>
// expand List__CompileError = List_<struct CompileError>
struct public CompileError(struct String message, struct Context context);
struct public CompileError(struct String message, struct Context context, struct List__CompileError errors);
struct String format(int depth, int index, struct List__CompileError sorted);
int depth();
struct String display();
struct String format(int depth);
#endif

