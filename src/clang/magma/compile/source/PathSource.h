#ifndef magma_compile_source_PathSource
#define magma_compile_source_PathSource
#include "../../../magma/collect/list/ListCollector.h"
#include "../../../magma/collect/list/List_.h"
#include "../../../magma/io/IOError.h"
#include "../../../magma/io/Path_.h"
#include "../../../magma/result/Result.h"
struct PathSource{
};
// expand List_<String>
// expand ListCollector<>
// expand Result<String, IOError>
List_<String> computeNamespace();
String computeName();
Location location();
Result<String, IOError> readString();
#endif
