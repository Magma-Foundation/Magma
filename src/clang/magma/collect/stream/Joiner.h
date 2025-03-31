#ifndef magma_collect_stream_Joiner
#define magma_collect_stream_Joiner
#include "../../../magma/option/None.h"
#include "../../../magma/option/Option.h"
#include "../../../magma/option/Some.h"
struct Joiner{
};
magma.collect.stream.public Joiner();
magma.option.Option<String> createInitial();
magma.option.Option<String> fold(magma.option.Option<String> maybeCurrent, String element);
auto __lambda0__();
// expand magma.collect.stream.Collector<String, magma.option.Option<String>>
// expand magma.option.Option<String>
// expand magma.option.Option<String>
// expand magma.option.Option<String>
// expand magma.option.None<>
// expand magma.option.Option<String>
// expand magma.option.Some<>
// expand magma.option.Option<String>
#endif

