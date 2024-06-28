package magma.compile.rule.result;

import magma.api.option.Option;
import magma.api.result.Err;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.compile.CompileError;
import magma.compile.Error_;
import magma.compile.attribute.Attributes;
import magma.compile.rule.ImmutableNode;
import magma.compile.rule.Node;

import java.util.function.Function;

public interface RuleResult {
    private static Err<Node, Error_> createNothingPresentError() {
        return new Err<>(new CompileError("Neither value nor error is present.", ""));
    }

    private static Result<Node, Error_> wrapInOk(Node inner) {
        return new Ok<>(inner);
    }

    Option<Error_> findError();

    Option<Attributes> findAttributes();

    Option<Node> tryCreate();

    RuleResult withType(String type);

    RuleResult mapErr(Function<Error_, Error_> mapper);

    default Result<Node, Error_> create() {
        return tryCreate().map(RuleResult::wrapInOk).orElseGet(this::wrapInErr);
    }

    private Result<Node, Error_> wrapInErr() {
        return findError().map(err -> new Err<ImmutableNode, Error_>(err)).orElseGet(RuleResult::createNothingPresentError);
    }
}
