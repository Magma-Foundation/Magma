package magma.app.compile;

import magma.api.result.Err;
import magma.api.result.Result;
import magma.api.result.Tuple;

public record PrefixRule(String prefix, SuffixRule childRule) implements Rule {
    static Err<Tuple<String, String>, CompileError> createPrefixErr(String input, String prefix) {
        return new Err<>(new CompileError("Prefix '" + prefix + "' not present", input));
    }

    @Override
    public Result<Tuple<String, String>, CompileError> apply(ParseState parseState, String stripped) {
        if (!stripped.startsWith(prefix())) return createPrefixErr(stripped, prefix());
        String right = stripped.substring(prefix().length());
        return childRule().apply(parseState, right);
    }
}