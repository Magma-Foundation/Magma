#ifndef magma_compile_transform_State
#define magma_compile_transform_State
#include "../../../windows/collect/list/Lists.h"
#include "../../../windows/collect/stream/Streams.h"
#include "../../../magma/collect/list/ListCollector.h"
#include "../../../magma/collect/list/List_.h"
#include "../../../magma/compile/Node.h"
#include "../../../magma/option/None.h"
#include "../../../magma/option/Option.h"
#include "../../../magma/option/Some.h"
struct State{
};
// expand magma.collect.list.List_<String>
// expand magma.collect.list.List_<magma.collect.list.List_<String>>
// expand magma.collect.list.List_<String>
// expand magma.collect.list.List_<String>
// expand magma.collect.list.List_<magma.compile.transform.Frame>
// expand magma.collect.list.List_<String>
// expand magma.option.Option<magma.collect.list.List_<String>>
// expand magma.collect.list.List_<String>
// expand magma.collect.list.List_<String>
magma.compile.transform.public State(magma.collect.list.List_<String> namespace, String name);
magma.compile.transform.State defineImport(magma.compile.Node import_);
magma.compile.transform.State clearImports();
magma.option.Option<magma.collect.list.List_<String>> qualifyName(String name);
magma.compile.transform.State defineType(magma.compile.Node type);
magma.compile.transform.State enter();
magma.compile.transform.State exit();
magma.compile.transform.boolean isTypeParamDefined(String type);
auto __lambda0__();
auto __lambda1__();
#endif

