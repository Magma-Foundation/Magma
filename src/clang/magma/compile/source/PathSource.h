#ifndef magma_compile_source_PathSource
#define magma_compile_source_PathSource
#include "../../../magma/collect/list/List_.h"
#include "../../../magma/collect/list/ListCollector.h"
#include "../../../magma/io/IOError.h"
#include "../../../magma/io/Path_.h"
#include "../../../magma/result/Result.h"
struct PathSource{
};
// expand magma.collect.list.List_<String>
// expand magma.collect.list.ListCollector<>
// expand magma.result.Result<String, magma.io.IOError>
magma.collect.list.List_<String> computeNamespace();
String computeName();
magma.result.Result<String, magma.io.IOError> readString();
#endif

