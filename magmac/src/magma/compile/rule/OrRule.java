package magma.compile.rule;

import magma.api.Err;
import magma.api.Result;
import magma.compile.CompileException;
import magma.compile.rule.result.EmptyRuleResult;
import magma.compile.rule.result.RuleResult;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public record OrRule(List<Rule> rules) implements Rule {
    @Override
    public RuleResult toNode(String input) {
        for (Rule rule : rules()) {
            RuleResult result = rule.toNode(input);
            if (result.findAttributes().isPresent()) {
                return result;
            }
        }
        return new EmptyRuleResult();
    }

    @Override
    public Result<String, CompileException> fromNode(Node node) {
        var results = rules.stream()
                .map(rule -> rule.fromNode(node))
                .toList();

        var anyOk = results.stream()
                .filter(Result::isOk)
                .findFirst();

        if (anyOk.isPresent()) return anyOk.get();

        var max = results.stream()
                .map(Result::findErr)
                .flatMap(Optional::stream)
                .max(Comparator.comparingInt(CompileException::calculateDepth));

        return max
                .<Result<String, CompileException>>map(Err::new)
                .orElseGet(() -> new Err<>(new CompileException("No errors found.")));
    }
}