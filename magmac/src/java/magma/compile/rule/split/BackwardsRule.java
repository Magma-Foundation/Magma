package magma.compile.rule.split;

import magma.api.result.Result;
import magma.compile.CompileError;
import magma.compile.CompileParentError;
import magma.compile.Error_;
import magma.compile.MultipleError;
import magma.compile.rule.Node;
import magma.compile.rule.Rule;
import magma.compile.rule.result.ErrorRuleResult;
import magma.compile.rule.result.RuleResult;
import magma.compile.rule.result.UntypedRuleResult;
import magma.java.JavaOptionals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record BackwardsRule(Rule leftRule, String slice, Rule rightRule) implements Rule {
    @Override
    public RuleResult toNode(String input) {
        var allIndexes = findAllIndexesReverse(input);
        var errors = new ArrayList<Error_>();

        for (Integer index : allIndexes) {
            var leftSlice = input.substring(0, index);
            var rightSlice = input.substring(index + slice.length());

            var leftResult = leftRule.toNode(leftSlice);
            if (JavaOptionals.toNative(leftResult.findError()).isPresent()) {
                errors.add(wrapError(leftSlice, rightSlice, JavaOptionals.toNative(leftResult.findError()).get()));
                continue;
            }

            var rightResult = rightRule.toNode(rightSlice);
            if (JavaOptionals.toNative(rightResult.findError()).isPresent()) {
                errors.add(wrapError(leftSlice, rightSlice, JavaOptionals.toNative(rightResult.findError()).get()));
                continue;
            }

            var optional = JavaOptionals.toNative(leftResult.findAttributes())
                    .flatMap(leftAttributes -> JavaOptionals.toNative(rightResult.findAttributes()).map(rightAttributes -> rightAttributes.merge(leftAttributes)))
                    .map(UntypedRuleResult::new);

            if (optional.isPresent()) {
                return optional.get();
            }
        }

        if (errors.isEmpty()) {
            return new ErrorRuleResult(new CompileError("No rules were present.", input));
        } else {
            return new ErrorRuleResult(new MultipleError(errors));
        }
    }

    private static CompileParentError wrapError(String leftSlice, String rightSlice, Error_ error) {
        return new CompileParentError("Invalid combination.", "[\"" + leftSlice + "\", \"" + rightSlice + "\"]", error);
    }

    private List<Integer> findAllIndexesReverse(String input) {
        List<Integer> indexes = new ArrayList<>();
        int index = input.indexOf(slice);
        while (index >= 0) {
            indexes.add(index);
            index = input.indexOf(slice, index + 1);
        }
        Collections.reverse(indexes);
        return indexes;
    }

    @Override
    public Result<String, Error_> fromNode(Node node) {
        return leftRule.fromNode(node).flatMapValue(leftResult -> rightRule.fromNode(node).mapValue(rightResult -> leftResult + slice + rightResult));
    }
}
