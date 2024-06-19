package magma.compile.rule.result;

import magma.api.result.Err;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.compile.CompileError;
import magma.compile.Error_;
import magma.compile.attribute.Attributes;
import magma.compile.rule.Node;

import java.util.Optional;
import java.util.function.Function;

public interface RuleResult {
    private static Err<Node, Error_> createNothingPresentError() {
        return new Err<>(new CompileError("Neither value nor error is present.", ""));
    }

    private static Result<Node, Error_> wrapInOk(Node inner) {
        return new Ok<>(inner);
    }

    Optional<String> findName();

    Optional<Error_> findError();

    Optional<Attributes> findAttributes();

    Optional<Node> tryCreate();

    RuleResult withType(String type);

    RuleResult mapErr(Function<Error_, Error_> mapper);

    default Result<Node, Error_> create() {
        return tryCreate()
                .map(RuleResult::wrapInOk)
                .orElseGet(this::wrapInErr);
    }

    private Result<Node, Error_> wrapInErr() {
        return findError()
                .map(err -> new Err<Node, Error_>(err))
                .orElseGet(RuleResult::createNothingPresentError);
    }
}
