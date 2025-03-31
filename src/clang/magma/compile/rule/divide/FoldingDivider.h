#ifndef magma_compile_rule_divide_FoldingDivider
#define magma_compile_rule_divide_FoldingDivider
#include "../../../../windows/collect/stream/Streams.h"
#include "../../../../magma/collect/list/ListCollector.h"
#include "../../../../magma/collect/list/List_.h"
#include "../../../../magma/option/Tuple.h"
struct FoldingDivider{magma.compile.rule.divide.Folder folder;
};
magma.compile.rule.divide.public FoldingDivider(magma.compile.rule.divide.Folder folder);
magma.collect.list.List_<String> divide(String input);
String join(String current, String element);
// expand magma.collect.list.List_<String>
#endif

