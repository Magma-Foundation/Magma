#include "LazyRule.h"
int __lambda0__(){return inner;
}
int __lambda1__(){return inner;
}
int __lambda2__(){return (CompileError("Child not set", context));
}
Result<Node, CompileError> parse(String input){return withChildSet(__lambda0__.parse(input), StringContext(input));
}
Result<String, CompileError> generate(Node node){return withChildSet(__lambda1__.generate(node), NodeContext(node));
}
Result<T, CompileError> withChildSet(Result<T, CompileError>(*mapper)(Rule), Context context){return child.map(mapper).orElseGet(__lambda2__);
}
void set(Rule child){this.child = new Some<>(child);
}
