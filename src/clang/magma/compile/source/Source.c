#include "Source.h"
// expand List__String = List_<struct String>
// expand Result_String_IOError = Result<struct String, struct IOError>
struct List__String computeNamespace();
struct String computeName();
struct Result_String_IOError readString();
