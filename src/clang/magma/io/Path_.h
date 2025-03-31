#ifndef magma_io_Path_
#define magma_io_Path_
#include "../../magma/collect/set/Set_.h"
#include "../../magma/collect/stream/Stream.h"
#include "../../magma/option/Option.h"
#include "../../magma/result/Result.h"
struct Path_{
};
int exists();
magma.result.Result<magma.collect.set.Set_<magma.io.Path_>, magma.io.IOError> walk();
magma.option.Option<magma.io.IOError> writeString(String output);
magma.option.Option<magma.io.IOError> createAsDirectories();
magma.io.Path_ resolve(String child);
magma.collect.stream.Stream<String> stream();
magma.io.Path_ relativize(magma.io.Path_ child);
int isRegularFile();
String asString();
magma.io.Path_ getParent();
magma.io.Path_ getFileName();
magma.result.Result<String, magma.io.IOError> readString();
// expand magma.result.Result<magma.collect.set.Set_<magma.io.Path_>, magma.io.IOError>
// expand magma.collect.set.Set_<magma.io.Path_>
// expand magma.collect.set.Set_<magma.io.Path_>
// expand magma.option.Option<magma.io.IOError>
// expand magma.option.Option<magma.io.IOError>
// expand magma.collect.stream.Stream<String>
// expand magma.result.Result<String, magma.io.IOError>
#endif

