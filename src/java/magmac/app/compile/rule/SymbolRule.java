package magmac.app.compile.rule;

import magmac.api.result.Err;
import magmac.api.result.Result;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.result.StringContext;
import magmac.app.error.CompileError;

public record SymbolRule(Rule childRule) implements Rule {
    @Override
    public Result<Node, CompileError> lex(String input) {
        if (SymbolRule.isSymbol(input)) {
            return this.childRule.lex(input);
        }
        else {
            return new Err<>(new CompileError("Not a symbol", new StringContext(input)));
        }
    }

    private static boolean isSymbol(String input) {
        int length = input.length();
        for (int i = 0; i < length; i++) {
            char c = input.charAt(i);
            if (Character.isLetter(c)) {
                continue;
            }
            return false;
        }
        return true;
    }

    @Override
    public Result<String, CompileError> generate(Node node) {
        return this.childRule.generate(node);
    }
}
