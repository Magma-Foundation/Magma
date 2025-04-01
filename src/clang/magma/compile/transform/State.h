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
struct State{magma.collect.list.List_<magma.collect.list.List_<String>> imports;magma.collect.list.List_<magma.compile.transform.Frame> frames;magma.compile.source.Location location;
};
// expand magma.collect.list.List_<magma.collect.list.List_<String>>
// expand magma.collect.list.List_<String>
// expand magma.collect.list.List_<String>
// expand magma.collect.list.List_<magma.compile.transform.Frame>
// expand magma.collect.list.List_<magma.collect.list.List_<String>>
// expand magma.collect.list.List_<String>
// expand magma.collect.list.List_<String>
// expand magma.collect.list.List_<magma.compile.transform.Frame>
// expand magma.option.Option<magma.collect.list.List_<String>>
// expand magma.collect.list.List_<String>
// expand magma.collect.list.List_<String>
// expand magma.collect.list.List_<String>
int __lambda0__();
int __lambda1__();
magma.compile.transform.public State(magma.compile.source.Location location, magma.collect.list.List_<magma.collect.list.List_<String>> imports, magma.collect.list.List_<magma.compile.transform.Frame> frames);
magma.compile.transform.public State(magma.compile.source.Location location);
magma.compile.transform.State defineImport(magma.compile.Node import_);
magma.compile.transform.State clearImports();
magma.option.Option<magma.collect.list.List_<String>> qualifyName(String name);
magma.compile.transform.State defineType(magma.compile.Node type);
magma.compile.transform.State enter();
magma.compile.transform.State exit();
int isTypeParamDefined(String type);
magma.collect.list.List_<String> namespace();
String name();
#endif
