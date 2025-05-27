package magmac.app.compile.rule;

import magmac.api.result.Err;
import magmac.api.result.Ok;
import magmac.api.result.Result;
import magmac.app.compile.Context;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.result.StringContext;
import magmac.app.error.CompileError;
import magmac.app.error.ImmutableCompileError;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public record OrRule(List<Rule> rules) implements Rule {
    private record State<T>(Optional<T> maybeValue, List<CompileError> errors) {
        private State() {
            this(Optional.empty(), new ArrayList<>());
        }

        State<T> withValue(T value) {
            if (this.maybeValue.isPresent()) {
                return this;
            }
            return new State<>(Optional.of(value), this.errors);
        }

        Result<T, CompileError> toResult(Context context) {
            return this.maybeValue
                    .<Result<T, CompileError>>map(value -> new Ok<>(value))
                    .orElseGet(() -> new Err<>(new ImmutableCompileError("Invalid combination", context, this.errors)));
        }

        public State<T> withError(CompileError error) {
            this.errors.add(error);
            return this;
        }
    }

    private static <T> State<T> foldElement(State<T> state, Rule rule, Function<Rule, Result<T, CompileError>> mapper) {
        return mapper.apply(rule).match(state::withValue, state::withError);
    }

    @Override
    public Result<Node, CompileError> lex(String input) {
        return this.foldAll(rule1 -> rule1.lex(input), new StringContext(input));
    }

    private <T> Result<T, CompileError> foldAll(Function<Rule, Result<T, CompileError>> mapper, Context context) {
        return this.rules.stream().reduce(new State<T>(),
                        (state, rule) -> OrRule.foldElement(state, rule, mapper),
                        (_, next) -> next)
                .toResult(context);
    }

    @Override
    public Result<String, CompileError> generate(Node node) {
        return this.foldAll(rule1 -> rule1.generate(node), new NodeContext(node));
    }
}
