#ifndef magma_compile_lang_StringLists
#define magma_compile_lang_StringLists
#include "../../../windows/collect/list/Lists.h"
#include "../../../windows/collect/stream/Streams.h"
#include "../../../magma/collect/list/ListCollector.h"
#include "../../../magma/collect/list/List_.h"
#include "../../../magma/compile/MapNode.h"
#include "../../../magma/compile/Node.h"
struct StringLists{
};
// expand magma.collect.list.List_<String>
// expand magma.collect.list.List_<String>
// expand magma.collect.list.ListCollector<String>
auto __lambda0__();
magma.compile.Node toQualified(magma.collect.list.List_<String> list);
magma.collect.list.List_<String> fromQualified(magma.compile.Node node);
#endif
