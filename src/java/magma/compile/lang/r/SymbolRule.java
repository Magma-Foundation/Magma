package magma.compile.lang.r;

import magma.compile.CompileError;
import magma.compile.Node;
import magma.compile.context.StringContext;
import magma.compile.rule.Rule;
import magma.result.Err;
import magma.result.Result;

public record SymbolRule(Rule rule) implements Rule {
    @Override
    public Result<Node, CompileError> parse(String input) {
        if (isSymbol(input)) {
            return rule.parse(input);
        } else {
            return new Err<>(new CompileError("Not a symbol", new StringContext(input)));
        }
    }

    private boolean isSymbol(String input) {
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == '_' || Character.isLetter(c)) continue;
            return false;
        }

        return true;
    }

    @Override
    public Result<String, CompileError> generate(Node node) {
        return rule.generate(node);
    }
}
