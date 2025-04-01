#ifndef magma_compile_source_Location
#define magma_compile_source_Location
#include "../../../magma/collect/list/List_.h"
struct Location{
};
// expand List_<String>
Location resolveSibling(String otherName);
Location resolveChild(String child);
#endif
