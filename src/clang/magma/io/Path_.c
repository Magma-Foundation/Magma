#include "Path_.h"
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
