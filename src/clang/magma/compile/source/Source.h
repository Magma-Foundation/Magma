#ifndef magma_compile_source_Source
#define magma_compile_source_Source
#include "../../../magma/io/IOError.h"
#include "../../../magma/result/Result.h"
struct Source{
};
// expand magma.result.Result<String, magma.io.IOError>
magma.compile.source.Location location();
magma.result.Result<String, magma.io.IOError> readString();
#endif
