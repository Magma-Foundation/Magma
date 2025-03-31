#include "OrState.h"
magma.compile.rule.tree.public OrState(){this((), Lists.empty());
}
magma.compile.rule.tree.OrState<T> withValue(T value){return ((value), errors);
}
magma.result.Result<T, magma.collect.list.List_<magma.compile.CompileError>> toResult(){return maybeValue.map(Ok.new).orElseGet(__lambda0__);
}
magma.compile.rule.tree.OrState<T> withError(magma.compile.CompileError error){return (maybeValue, errors.add(error));
}
auto __lambda0__();

