package magmac.app.compile.rule;

import magmac.api.result.Err;
import magmac.api.result.Result;
import magmac.app.compile.node.Node;
import magmac.app.compile.error.context.StringContext;
import magmac.app.compile.error.CompileError;
import magmac.app.error.ImmutableCompileError;

public record SymbolRule(Rule childRule) implements Rule {
    private static boolean isSymbol(String input) {
        int length = input.length();
        for (int i = 0; i < length; i++) {
            char c = input.charAt(i);
            if (Character.isLetter(c) || (0 != i && Character.isDigit(c))) {
                continue;
            }
            return false;
        }
        return true;
    }

    @Override
    public Result<Node, CompileError> lex(String input) {
        if (SymbolRule.isSymbol(input)) {
            return this.childRule.lex(input);
        }
        else {
            return new Err<>(new ImmutableCompileError("Not a symbol", new StringContext(input)));
        }
    }

    @Override
    public Result<String, CompileError> generate(Node node) {
        return this.childRule.generate(node);
    }
}
