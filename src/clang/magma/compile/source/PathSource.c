#include "PathSource.h"
List_<struct String> computeNamespace(}{Path_ relative = sourceDirectory.relativize(source);
        Path_ parent = relative.getParent();
        return parent.stream().collect(new ListCollector<>());}struct String computeName(}{String nameWithExt = source().getFileName().asString();
        return nameWithExt.substring(0, nameWithExt.lastIndexOf());}Result<struct String, struct IOError> readString(}{return source.readString();}