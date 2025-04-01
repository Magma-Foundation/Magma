#include "Frame.h"
int __lambda0__(){return node;
}
public Frame(){this(Lists.empty());
}
Frame defineType(Node type){return Frame(types.add(type));
}
int isTypeDefined(String typeParam){return types.stream().map(__lambda0__.findString("value")).flatMap(Streams.fromOption).anyMatch(typeParam.equals);
}
