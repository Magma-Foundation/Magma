#include <magma/error/ApplicationError.h>
#include <magma/error/CompileError.h>
#include <magma/error/ThrowableError.h>
#include <magma/java/result/JavaResults.h>
#include <magma/option/None.h>
#include <magma/option/Option.h>
#include <magma/option/Some.h>
#include <magma/result/Err.h>
#include <magma/result/Ok.h>
#include <magma/result/Result.h>
#include <java/io/IOException.h>
#include <java/nio/file/Files.h>
#include <java/nio/file/Path.h>
#include <java/nio/file/Paths.h>
#include <java/util/ArrayList.h>
#include <java/util/Arrays.h>
#include <java/util/List.h>
#include <java/util/Set.h>
#include <java/util/regex/Pattern.h>
#include <java/util/stream/Collectors.h>
#include <java/util/stream/Stream.h>
struct Main {
	public static final Path SOURCE_DIRECTORY = (*Paths.get)();
	public static void (*main)();
};
