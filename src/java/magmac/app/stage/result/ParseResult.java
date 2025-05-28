package magmac.app.stage.result;

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.stage.unit.ParseUnit;

import java.util.function.Supplier;

public interface ParseResult {
    CompileResult<ParseUnit<Node>> orElseGet(Supplier<ParseUnit<Node>> other);
}
