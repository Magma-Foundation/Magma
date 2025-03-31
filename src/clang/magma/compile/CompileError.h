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
struct CompileError{String message;magma.option.Option<magma.compile.context.Context> maybeContext;magma.collect.list.List_<magma.compile.CompileError> errors;
};
// expand magma.option.Option<magma.compile.context.Context>
// expand magma.collect.list.List_<magma.compile.CompileError>
// expand magma.collect.list.List_<magma.compile.CompileError>
// expand magma.collect.list.List_<magma.compile.CompileError>
magma.compile.public CompileError(String message, magma.compile.context.Context maybeContext);
magma.compile.public CompileError(String message, magma.compile.context.Context maybeContext, magma.collect.list.List_<magma.compile.CompileError> errors);
magma.compile.public CompileError(String message);
String format(int depth, int index, magma.collect.list.List_<magma.compile.CompileError> sorted);
int depth();
String display();
String format(int depth);
#endif
