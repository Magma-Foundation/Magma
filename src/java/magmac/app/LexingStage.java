package magmac.app;

import magmac.api.result.Result;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.error.ApplicationError;
import magmac.app.error.ThrowableError;
import magmac.app.io.sources.Sources;
import magmac.app.stage.lexer.Lexer;
import magmac.app.stage.unit.UnitSet;

import java.util.function.Function;

public record LexingStage<T>(Lexer lexer, Function<Node, CompileResult<T>> deserializer) {
    public Result<UnitSet<T>, ApplicationError> getUnitSetApplicationErrorResult(Sources sources1) {
        return sources1.readAll()
                .mapErr(ThrowableError::new)
                .mapErr(ApplicationError::new)
                .flatMapValue(this::getUnitSetApplicationErrorResult);
    }

    private Result<UnitSet<T>, ApplicationError> getUnitSetApplicationErrorResult(UnitSet<String> units) {
        return this.lexer.apply(units)
                .flatMapValue((UnitSet<Node> roots) -> roots.mapValuesToResult(this.deserializer))
                .toResult()
                .mapErr(ApplicationError::new);
    }
}