package magma.app.compile.rule;

import magma.api.result.Err;
import magma.api.result.Result;
import magma.api.result.Tuple;
import magma.app.compile.CompileError;
import magma.app.compile.ParseState;

public record PrefixRule(String prefix, SuffixRule childRule) implements Rule {
    static Err<Tuple<String, String>, CompileError> createPrefixErr(String input, String prefix) {
        return new Err<>(new CompileError("Prefix '" + prefix + "' not present", input));
    }

    @Override
    public Result<Tuple<String, String>, CompileError> apply(ParseState state, String input) {
        if (input.startsWith(prefix)) {
            String slice = input.substring(prefix.length());
            return childRule.apply(state, slice);
        }

        return createPrefixErr(input, prefix);
    }
}