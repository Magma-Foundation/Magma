#include "Path_.h"
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
