#ifndef magma_result_Ok
#define magma_result_Ok
#include "../../magma/option/None.h"
#include "../../magma/option/Option.h"
#include "../../magma/option/Some.h"
#include "../../magma/option/Tuple.h"
magma.option.Option<T> findValue();
magma.option.Option<X> findError();
magma.result.Result<magma.result.R, X> mapValue(magma.result.R(*mapper)(T));
magma.result.Result<magma.result.R, X> flatMapValue(magma.result.Result<magma.result.R, X>(*mapper)(T));
magma.result.Result<T, magma.result.R> mapErr(magma.result.R(*mapper)(X));
magma.result.R match(magma.result.R(*whenOk)(T), magma.result.R(*whenErr)(X));
magma.result.Result<magma.option.Tuple<T, magma.result.R>, X> and(magma.result.Result<magma.result.R, X>(*other)());
magma.result.void consume(void(*whenOk)(T), void(*whenErr)(X));
auto __lambda0__();
// expand magma.result.Result<T, X>
// expand magma.option.Option<T>
// expand magma.option.Some<>
// expand magma.option.Option<X>
// expand magma.option.None<>
// expand magma.result.Result<magma.result.R, X>
// expand magma.result.Ok<>
// expand magma.result.Result<magma.result.R, X>
// expand magma.result.Result<magma.result.R, X>
// expand magma.result.Result<T, magma.result.R>
// expand magma.result.Ok<>
// expand magma.result.Result<magma.option.Tuple<T, magma.result.R>, X>
// expand magma.option.Tuple<T, magma.result.R>
// expand magma.option.Tuple<T, magma.result.R>
// expand magma.result.Result<magma.result.R, X>
#endif

