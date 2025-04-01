#include "PathSource.h"
List_<String> computeNamespace(){Path_ relative = sourceDirectory.relativize(source);
        Path_ parent = relative.getParent();return parent.stream().collect(());
}
String computeName(){String nameWithExt = source().getFileName().asString();return nameWithExt.substring(0, nameWithExt.lastIndexOf("."));
}
Location location(){return Location(computeNamespace(), computeName());
}
Result<String, IOError> readString(){return source.readString();
}
