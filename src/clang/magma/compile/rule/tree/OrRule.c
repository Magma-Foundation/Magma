#include "OrRule.h"
Result<struct T, struct CompileError> apply(Result<struct T, struct CompileError>(*applicator)(struct Rule), Supplier<struct Context> context}{return rules.stream()
                .foldWithInitial(new OrState<T>(), (orState, rule) -> applicator.apply(rule).match(orState::withValue, orState::withError))
                .toResult()
                .<Result<T, CompileError>>match(Ok::new, errors -> new Err<>(new CompileError(, context.get(), errors)));}Result<struct Node, struct CompileError> parse(struct String input}{return apply(rule -> rule.parse(input), () -> new StringContext(input));}Result<struct String, struct CompileError> generate(struct Node input}{return apply(rule -> rule.generate(input), () -> new NodeContext(input));}