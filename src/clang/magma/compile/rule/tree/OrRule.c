#include "OrRule.h"
int __lambda0__(){return applicator;
}
int __lambda1__(){return (CompileError("No valid combination present", context.get(), errors));
}
int __lambda2__(){return rule;
}
int __lambda3__(){return StringContext(input);
}
int __lambda4__(){return rule;
}
int __lambda5__(){return NodeContext(input);
}
Result<T, CompileError> apply(Result<T, CompileError>(*applicator)(Rule), Context(*context)()){return rules.stream().foldWithInitial((), __lambda0__.apply(rule).match(orState.withValue, orState.withError)).toResult().match(Ok.new, __lambda1__);
}
Result<Node, CompileError> parse(String input){return apply(__lambda2__.parse(input), __lambda3__);
}
Result<String, CompileError> generate(Node input){return apply(__lambda4__.generate(input), __lambda5__);
}
