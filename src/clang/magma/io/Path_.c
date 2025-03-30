#include "Path_.h"
expand Result_Set__Path__IOError
expand Set__Path_
expand Option_IOError
expand Option_IOError
expand Stream_String
expand Result_String_IOError
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
