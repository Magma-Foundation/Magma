package magma.compile.rule.result;

import magma.api.Err;
import magma.api.Ok;
import magma.api.Result;
import magma.compile.CompileException;
import magma.compile.rule.Node;
import magma.compile.rule.Rule;
import magma.compile.rule.Rules;

import java.util.Optional;

public final class LastRule extends SplitRule implements Rule {
    public LastRule(Rule leftRule, String slice, Rule rightRule) {
        super(leftRule, slice, rightRule);
    }

    @Override
    protected Optional<Integer> computeIndex(String input) {
        return Rules.wrapIndex(input.lastIndexOf(slice));
    }

    @Override
    public Result<String, CompileException> fromNode(Node node) {
        return fromNode0(node)
                .<Result<String, CompileException>>map(Ok::new)
                .orElseGet(() -> new Err<>(new CompileException("Cannot render: " + node)));
    }

    private Optional<String> fromNode0(Node node) {
        throw new UnsupportedOperationException();
    }
}