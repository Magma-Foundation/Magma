package magma.app.compile.rule;

import magma.api.result.Result;
import magma.api.result.Tuple;
import magma.app.compile.CompileError;
import magma.app.compile.ParseState;

public record StripRule(Rule rule) implements Rule {
    @Override
    public Result<Tuple<String, String>, CompileError> apply(ParseState state, String input) {
        String stripped = input.strip();
        return rule.apply(state, stripped);
    }
}