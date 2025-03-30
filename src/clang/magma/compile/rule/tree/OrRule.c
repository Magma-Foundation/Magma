#include "OrRule.h"
struct Result_T_CompileError apply(struct Result_T_CompileError(*applicator)(struct Rule), struct Context(*context)()){return rules.stream()
                .foldWithInitial(new OrState<T>(), (orState, rule) -> applicator.apply(rule).match(orState::withValue, orState::withError))
                .toResult()
                .<Result<T, CompileError>>match(Ok::new, errors -> new Err<>(new CompileError(, context.get(), errors)));
}
struct Result_Node_CompileError parse(struct String input){return apply(rule -> rule.parse(input), () -> new StringContext(input));
}
struct Result_String_CompileError generate(struct Node input){return apply(rule -> rule.generate(input), () -> new NodeContext(input));
}

