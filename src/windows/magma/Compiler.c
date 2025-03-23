#include <magma/error/CompileError.h>
#include <magma/result/Result.h>
struct Compiler {
	Result<String, CompileError> (*compile)();
};
