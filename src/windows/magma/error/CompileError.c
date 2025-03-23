#include <java/util/Collections.h>
#include <java/util/Comparator.h>
#include <java/util/List.h>
#include <java/util/stream/Collectors.h>
#include <java/util/stream/IntStream.h>
struct CompileError {
	int message;
	int context;
	int children;
	public (*CompileError)();
};
