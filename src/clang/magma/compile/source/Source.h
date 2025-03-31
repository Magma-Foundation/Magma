#ifndef magma_compile_source_Source
#define magma_compile_source_Source
#include "../../../magma/collect/list/List_.h"
#include "../../../magma/io/IOError.h"
#include "../../../magma/result/Result.h"
struct Source{
};
magma.collect.list.List_<String> computeNamespace();
String computeName();
magma.result.Result<String, magma.io.IOError> readString();
// expand magma.collect.list.List_<String>
// expand magma.result.Result<String, magma.io.IOError>
#endif

