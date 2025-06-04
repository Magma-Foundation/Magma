package magmac.app.stage.result;

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.stage.unit.ParseUnit;

import java.util.function.Supplier;

/**
 * Result of a single pass through the parser.
 */
public interface ParseResult {
    /**
     * Returns this result or falls back to {@code other} when empty.
     */
    CompileResult<ParseUnit<Node>> orElseGet(Supplier<ParseUnit<Node>> other);
}
