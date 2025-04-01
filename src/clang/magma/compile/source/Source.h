#ifndef magma_compile_source_Source
#define magma_compile_source_Source
#include "../../../magma/io/IOError.h"
#include "../../../magma/result/Result.h"
struct Source{
};
// expand Result<String, IOError>
Location location();
Result<String, IOError> readString();
#endif
