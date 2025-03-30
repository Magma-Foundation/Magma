#ifndef magma_io_Path_
#define magma_io_Path_
#include "../../magma/collect/set/Set_.h"
#include "../../magma/collect/stream/Stream.h"
#include "../../magma/option/Option.h"
#include "../../magma/result/Result.h"
struct Path_{};
int exists();
Result<Set_<struct Path_>, struct IOError> walk();
Option<struct IOError> writeString(struct String output);
Option<struct IOError> createAsDirectories();
struct Path_ resolve(struct String child);
Stream<struct String> stream();
struct Path_ relativize(struct Path_ child);
int isRegularFile();
struct String asString();
struct Path_ getParent();
struct Path_ getFileName();
Result<struct String, struct IOError> readString();
#endif
