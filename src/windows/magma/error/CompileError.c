#include <java/util/Collections.h>
#include <java/util/Comparator.h>
#include <java/util/List.h>
#include <java/util/stream/Collectors.h>
#include <java/util/stream/IntStream.h>
struct CompileError {
	String message;
	String context;
	List<CompileError> children;
	public (*CompileError)();
};
