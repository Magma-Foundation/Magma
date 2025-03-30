#include "Path_.h"
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
