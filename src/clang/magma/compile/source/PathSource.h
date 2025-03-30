#ifndef magma_compile_source_PathSource
#define magma_compile_source_PathSource
#include "../../../magma/collect/list/List_.h"
#include "../../../magma/collect/list/ListCollector.h"
#include "../../../magma/io/IOError.h"
#include "../../../magma/io/Path_.h"
#include "../../../magma/result/Result.h"
struct PathSource{};
struct List__String computeNamespace();
struct String computeName();
struct Result_String_IOError readString();
#endif
