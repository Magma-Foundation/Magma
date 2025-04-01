#include "LazyRule.h"
int __lambda0__(){return inner;
}
int __lambda1__(){return inner;
}
int __lambda2__(){return (CompileError("Child not set", context));
}
magma.result.Result<magma.compile.Node, magma.compile.CompileError> parse(String input){return withChildSet(__lambda0__.parse(input), StringContext(input));
}
magma.result.Result<String, magma.compile.CompileError> generate(magma.compile.Node node){return withChildSet(__lambda1__.generate(node), NodeContext(node));
}
magma.result.Result<magma.compile.rule.T, magma.compile.CompileError> withChildSet(magma.result.Result<magma.compile.rule.T, magma.compile.CompileError>(*mapper)(magma.compile.rule.Rule), magma.compile.context.Context context){return child.map(mapper).orElseGet(__lambda2__);
}
magma.compile.rule.void set(magma.compile.rule.Rule child){this.child = new Some<>(child);
}
