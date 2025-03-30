#ifndef magma_io_Path_
#define magma_io_Path_
#include "../../magma/collect/set/Set_.h"
#include "../../magma/collect/stream/Stream.h"
#include "../../magma/option/Option.h"
#include "../../magma/result/Result.h"
struct Path_{
};
// expand Result_Set__Path__IOError = Result<struct Set__Path_, struct IOError>
// expand Set__Path_ = Set_<struct Path_>
// expand Option_IOError = Option<struct IOError>
// expand Option_IOError = Option<struct IOError>
// expand Stream_String = Stream<struct String>
// expand Result_String_IOError = Result<struct String, struct IOError>
int exists();
struct Result_Set__Path__IOError walk();
struct Option_IOError writeString(struct String output);
struct Option_IOError createAsDirectories();
struct Path_ resolve(struct String child);
struct Stream_String stream();
struct Path_ relativize(struct Path_ child);
int isRegularFile();
struct String asString();
struct Path_ getParent();
struct Path_ getFileName();
struct Result_String_IOError readString();
#endif

