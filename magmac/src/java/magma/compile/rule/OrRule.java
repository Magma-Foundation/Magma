package magma.compile.rule;

import magma.api.result.Err;
import magma.api.result.Result;
import magma.compile.CompileError;
import magma.compile.Error_;
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
            return new ErrorRuleResult(new CompileError("No rules were present.", input));
        } else {
            return new ErrorRuleResult(new MultipleError(errors));
        }
    }

    @Override
    public Result<String, Error_> fromNode(Node node) {
        var results = new ArrayList<Result<String, Error_>>();

        for (var rule : rules) {
            var result = rule.fromNode(node);
            results.add(result);
        }

        Optional<Result<String, Error_>> anyOk = Optional.empty();
        for (Result<String, Error_> result : results) {
            if (result.isOk()) {
                anyOk = Optional.of(result);
                break;
            }
        }

        return anyOk.orElseGet(() -> toError(results));

    }
}