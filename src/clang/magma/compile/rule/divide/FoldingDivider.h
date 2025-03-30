#ifndef magma_compile_rule_divide_FoldingDivider
#define magma_compile_rule_divide_FoldingDivider
#include "../../../../windows/collect/stream/Streams.h"
#include "../../../../magma/collect/list/ListCollector.h"
#include "../../../../magma/collect/list/List_.h"
#include "../../../../magma/option/Tuple.h"
struct FoldingDivider{struct Folder folder};
struct public FoldingDivider(struct Folder folder);
struct List__String divide(struct String input);
struct String join(struct String current, struct String element);
#endif
