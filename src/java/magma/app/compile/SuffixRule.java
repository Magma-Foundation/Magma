package magma.app.compile;

import magma.api.result.Result;
import magma.api.result.Tuple;

public record SuffixRule(Rule child, String suffix) implements Rule {
    @Override
    public Result<Tuple<String, String>, CompileError> apply(ParseState parseState, String input) {
        if (!input.endsWith(suffix())) return Compiler.createSuffixErr(input, suffix());
        String left = input.substring(0, input.length() - suffix().length());
        return child().apply(parseState, left);
    }
}