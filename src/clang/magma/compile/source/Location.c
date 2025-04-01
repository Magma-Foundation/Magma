#include "Location.h"
Location resolveSibling(String otherName){return Location(namespace, otherName);
}
Location resolveChild(String child){return Location(namespace.add(name.toLowerCase()), child);
}
