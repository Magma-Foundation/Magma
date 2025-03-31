#include "Ok.h"
magma.option.Option<T> findValue(){return (value);
}
magma.option.Option<X> findError(){return ();
}
magma.result.Result<magma.result.R, X> mapValue(magma.result.R(*mapper)(T)){return (mapper.apply(value));
}
magma.result.Result<magma.result.R, X> flatMapValue(magma.result.Result<magma.result.R, X>(*mapper)(T)){return mapper.apply(value);
}
magma.result.Result<T, magma.result.R> mapErr(magma.result.R(*mapper)(X)){return (value);
}
magma.result.R match(magma.result.R(*whenOk)(T), magma.result.R(*whenErr)(X)){return whenOk.apply(value);
}
magma.result.Result<magma.option.Tuple<T, magma.result.R>, X> and(magma.result.Result<magma.result.R, X>(*other)()){return other.get().mapValue(__lambda0__);
}
magma.result.void consume(void(*whenOk)(T), void(*whenErr)(X)){whenOk.accept(value);
}
auto __lambda0__();

