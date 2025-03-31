#include "Some.h"
magma.option.Option<magma.option.R> map(magma.option.R(*mapper)(T)){return (mapper.apply(value));
}
T orElseGet(T(*other)()){return value;
}
magma.option.Tuple<magma.option.Boolean, T> toTuple(T other){return (true, value);
}
magma.option.void ifPresent(void(*consumer)(T)){consumer.accept(value);
}
T orElse(T other){return value;
}
magma.option.Option<T> filter(int(*predicate)(T)){return predicate.test(value)?this:();
}
magma.option.boolean isPresent(){return true;
}
magma.option.R match(magma.option.R(*ifPresent)(T), magma.option.R(*ifEmpty)()){return ifPresent.apply(value);
}
magma.option.boolean isEmpty(){return false;
}
magma.option.Option<T> or(magma.option.Option<T>(*other)()){return this;
}
magma.option.Option<magma.option.R> flatMap(magma.option.Option<magma.option.R>(*mapper)(T)){return mapper.apply(value);
}

