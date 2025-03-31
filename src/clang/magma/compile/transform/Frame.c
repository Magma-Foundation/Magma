#include "Frame.h"
magma.compile.transform.public Frame(){this(Lists.empty());
}
magma.compile.transform.Frame defineType(magma.compile.Node type){return Frame(types.add(type));
}
int isTypeDefined(String typeParam){return types.stream().map(__lambda0__.findString("value")).flatMap(Streams.fromOption).anyMatch(typeParam.equals);
}
auto __lambda0__();

