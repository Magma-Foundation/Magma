#ifndef magma_io_Path_
#define magma_io_Path_
#include "../../magma/collect/set/Set_.h"
#include "../../magma/collect/stream/Stream.h"
#include "../../magma/option/Option.h"
#include "../../magma/result/Result.h"
struct Path_{
};
// expand Result<Set_<Path_>, IOError>
// expand Set_<Path_>
// expand Set_<Path_>
// expand Option<IOError>
// expand Option<IOError>
// expand Stream<String>
// expand Result<String, IOError>
int exists();
Result<Set_<Path_>, IOError> walk();
Option<IOError> writeString(String output);
Option<IOError> createAsDirectories();
Path_ resolve(String child);
Stream<String> stream();
Path_ relativize(Path_ child);
int isRegularFile();
String asString();
Path_ getParent();
Path_ getFileName();
Result<String, IOError> readString();
#endif
