package magmac.app.stage;

import magmac.app.compile.error.CompileResult;

import java.util.function.Supplier;

public interface ParseResult {
    CompileResult<ParseUnit> orElseGet(Supplier<ParseUnit> other);
}
