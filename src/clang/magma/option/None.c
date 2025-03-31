#include "None.h"
magma.option.Option<magma.option.R> map(magma.option.R(*mapper)(T)){return ();
}
T orElseGet(T(*other)()){return other.get();
}
magma.option.Tuple<magma.option.Boolean, T> toTuple(T other){return (false, other);
}
magma.option.void ifPresent(void(*consumer)(T)){
}
T orElse(T other){return other;
}
magma.option.Option<T> filter(int(*predicate)(T)){return ();
}
magma.option.boolean isPresent(){return false;
}
magma.option.R match(magma.option.R(*ifPresent)(T), magma.option.R(*ifEmpty)()){return ifEmpty.get();
}
magma.option.boolean isEmpty(){return true;
}
magma.option.Option<T> or(magma.option.Option<T>(*other)()){return other.get();
}
magma.option.Option<magma.option.R> flatMap(magma.option.Option<magma.option.R>(*mapper)(T)){return ();
}

