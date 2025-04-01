#ifndef magma_compile_CompileError
#define magma_compile_CompileError
#include "../../windows/collect/list/Lists.h"
#include "../../windows/collect/string/Strings.h"
#include "../../magma/collect/list/List_.h"
#include "../../magma/collect/stream/Joiner.h"
#include "../../magma/collect/stream/head/HeadedStream.h"
#include "../../magma/collect/stream/head/RangeHead.h"
#include "../../magma/compile/context/Context.h"
#include "../../magma/error/Error.h"
#include "../../magma/option/None.h"
#include "../../magma/option/Option.h"
#include "../../magma/option/Some.h"
struct CompileError{String message;Option<Context> maybeContext;List_<CompileError> errors;
};
// expand Option<Context>
// expand List_<CompileError>
// expand List_<CompileError>
// expand List_<CompileError>
public CompileError(String message, Context maybeContext);
public CompileError(String message, Context maybeContext, List_<CompileError> errors);
public CompileError(String message);
String format(int depth, int index, List_<CompileError> sorted);
int depth();
String display();
String format(int depth);
#endif
