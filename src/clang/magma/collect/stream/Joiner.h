#ifndef magma_collect_stream_Joiner
#define magma_collect_stream_Joiner
#include "../../../magma/option/None.h"
#include "../../../magma/option/Option.h"
#include "../../../magma/option/Some.h"
struct Joiner{
};
// expand magma.collect.stream.Collector<String, magma.option.Option<String>>
// expand magma.option.Option<String>
// expand magma.option.Option<String>
// expand magma.option.Option<String>
// expand magma.option.None<>
// expand magma.option.Option<String>
// expand magma.option.Some<>
// expand magma.option.Option<String>
auto __lambda0__();
magma.collect.stream.public Joiner();
magma.option.Option<String> createInitial();
magma.option.Option<String> fold(magma.option.Option<String> maybeCurrent, String element);
#endif
