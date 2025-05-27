package magmac.app.compile.rule;

import magmac.api.result.Result;
import magmac.app.compile.error.CompileError;
import magmac.app.compile.error.CompileErrors;
import magmac.app.compile.node.Node;

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
            return CompileErrors.createStringError("Not a symbol", input);
        }
    }

    @Override
    public Result<String, CompileError> generate(Node node) {
        return this.childRule.generate(node);
    }
}
