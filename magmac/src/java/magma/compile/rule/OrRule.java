package magma.compile.rule;

import magma.api.Err;
import magma.api.Result;
import magma.compile.GeneratingException;
import magma.compile.Error_;
import magma.compile.JavaError;
import magma.compile.MultipleError;
import magma.compile.rule.result.ErrorRuleResult;
import magma.compile.rule.result.RuleResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record OrRule(List<Rule> rules) implements Rule {
    private static Err<String, Error_> toError(List<Result<String, Error_>> results) {
        return new Err<>(new MultipleError(results.stream()
                .map(Result::findErr)
                .flatMap(Optional::stream)
                .toList()));
    }

    @Override
    public RuleResult toNode(String input) {
        var errors = new ArrayList<Error_>();
        for (Rule rule : rules()) {
            var result = rule.toNode(input);
            if (result.findAttributes().isPresent()) {
                return result;
            }

            result.findError().ifPresent(errors::add);
        }

        if (errors.isEmpty()) {
            return new ErrorRuleResult(new JavaError(new GeneratingException("No rules were present.")));
        } else {
            return new ErrorRuleResult(new MultipleError(errors));
        }
    }

    @Override
    public Result<String, Error_> fromNode(Node node) {
        var results = rules.stream()
                .map(rule -> rule.fromNode(node))
                .toList();

        var anyOk = results.stream()
                .filter(Result::isOk)
                .findFirst();

        return anyOk.orElseGet(() -> toError(results));

    }
}