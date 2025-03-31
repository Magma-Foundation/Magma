#include "PathSource.h"
magma.collect.list.List_<String> computeNamespace(){Path_ relative = sourceDirectory.relativize(source);
        Path_ parent = relative.getParent();return parent.stream().collect(());
}
String computeName(){String nameWithExt = source().getFileName().asString();return nameWithExt.substring(0, nameWithExt.lastIndexOf("."));
}
magma.result.Result<String, magma.io.IOError> readString(){return source.readString();
}

