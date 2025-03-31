#include "Err.h"
magma.option.Option<T> findValue(){return ();
}
magma.option.Option<X> findError(){return (error);
}
magma.result.Result<magma.result.R, X> mapValue(magma.result.R(*mapper)(T)){return (error);
}
magma.result.Result<magma.result.R, X> flatMapValue(magma.result.Result<magma.result.R, X>(*mapper)(T)){return (error);
}
magma.result.Result<T, magma.result.R> mapErr(magma.result.R(*mapper)(X)){return (mapper.apply(error));
}
magma.result.R match(magma.result.R(*whenOk)(T), magma.result.R(*whenErr)(X)){return whenErr.apply(error);
}
magma.result.Result<magma.option.Tuple<T, magma.result.R>, X> and(magma.result.Result<magma.result.R, X>(*other)()){return (error);
}
magma.result.void consume(void(*whenOk)(T), void(*whenErr)(X)){whenErr.accept(error);
}

