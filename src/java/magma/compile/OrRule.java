package magma.compile;

import magma.collect.list.List_;
import magma.result.Err;
import magma.result.Ok;
import magma.result.Result;

public record OrRule(List_<Rule> rules) implements Rule {
    static OrState fold(String input, OrState orState, Rule rule) {
        return rule.compile(input)
                .findValue()
                .map(orState::withValue)
                .orElse(orState);
    }

    @Override
    public Result<String, CompileException> compile(String input) {
        return rules.stream()
                .foldWithInitial(new OrState(), (orState, rule) -> fold(input, orState, rule))
                .toOption().<Result<String, CompileException>>match(Ok::new, () -> new Err<>(new CompileException("Invalid root segment", input)));
    }
}