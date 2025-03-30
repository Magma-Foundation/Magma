#ifndef magma_compile_source_Source
#define magma_compile_source_Source
#include "../../../magma/collect/list/List_.h"
#include "../../../magma/io/IOError.h"
#include "../../../magma/result/Result.h"
struct Source{};
List_<struct String> computeNamespace();
struct String computeName();
Result<struct String, struct IOError> readString();
#endif
