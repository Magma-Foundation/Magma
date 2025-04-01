#ifndef magma_compile_transform_State
#define magma_compile_transform_State
#include "../../../windows/collect/list/Lists.h"
#include "../../../windows/collect/stream/Streams.h"
#include "../../../magma/collect/list/ListCollector.h"
#include "../../../magma/collect/list/List_.h"
#include "../../../magma/compile/Node.h"
#include "../../../magma/compile/source/Location.h"
#include "../../../magma/option/None.h"
#include "../../../magma/option/Option.h"
#include "../../../magma/option/Some.h"
struct State{List_<List_<String>> imports;List_<Frame> frames;Location location;
};
// expand List_<List_<String>>
// expand List_<String>
// expand List_<String>
// expand List_<Frame>
// expand List_<List_<String>>
// expand List_<String>
// expand List_<String>
// expand List_<Frame>
// expand Option<List_<String>>
// expand List_<String>
// expand List_<String>
// expand List_<String>
int __lambda0__();
int __lambda1__();
public State(Location location, List_<List_<String>> imports, List_<Frame> frames);
public State(Location location);
State defineImport(Node import_);
State clearImports();
Option<List_<String>> qualifyName(String name);
State defineType(Node type);
State enter();
State exit();
int isTypeParamDefined(String type);
List_<String> namespace();
String name();
#endif
