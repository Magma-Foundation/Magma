#include "Location.h"
magma.compile.source.Location resolveSibling(String otherName){return Location(namespace, otherName);
}
magma.compile.source.Location resolveChild(String child){return Location(namespace.add(name.toLowerCase()), child);
}
