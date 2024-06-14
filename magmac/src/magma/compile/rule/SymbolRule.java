package magma.compile.rule;

import magma.api.Result;
import magma.compile.CompileError;
import magma.compile.Error_;
import magma.compile.rule.result.ErrorRuleResult;
import magma.compile.rule.result.RuleResult;

public record SymbolRule(Rule child) implements Rule {
    @Override
    public RuleResult toNode(String input) {
        if (isSymbol(input)) return child.toNode(input);
        return new ErrorRuleResult(new CompileError("Not a symbol.", input));
    }

    private boolean isSymbol(String input) {
        for (int i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (!Character.isLetter(c)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public Result<String, Error_> fromNode(Node node) {
        return child.fromNode(node);
    }
}
