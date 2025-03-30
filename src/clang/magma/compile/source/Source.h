#ifndef magma_compile_source_Source
#define magma_compile_source_Source
#include "../../../magma/collect/list/List_.h"
#include "../../../magma/io/IOError.h"
#include "../../../magma/result/Result.h"
struct Source{};
// expand List__String = List_<struct String>
// expand Result_String_IOError = Result<struct String, struct IOError>
struct List__String computeNamespace();
struct String computeName();
struct Result_String_IOError readString();
#endif
