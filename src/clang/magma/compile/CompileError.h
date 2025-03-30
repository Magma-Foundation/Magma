#ifndef magma_compile_CompileError
#define magma_compile_CompileError
#include "../../windows/collect/list/Lists.h"
#include "../../magma/collect/list/List_.h"
#include "../../magma/collect/stream/Joiner.h"
#include "../../magma/collect/stream/head/HeadedStream.h"
#include "../../magma/collect/stream/head/RangeHead.h"
#include "../../magma/compile/context/Context.h"
#include "../../magma/error/Error.h"
struct CompileError{struct String messagestruct Context contextList_<struct CompileError> errors};
struct public CompileError(struct String message, struct Context context);
struct public CompileError(struct String message, struct Context context, List_<struct CompileError> errors);
struct String format(struct int depth, struct int index, List_<struct CompileError> sorted);
struct int depth();
struct String display();
struct String format(struct int depth);
#endif
