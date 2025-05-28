package magmac.app.stage;

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;

import java.util.function.Supplier;

public interface ParseResult {
    CompileResult<ParseUnit<Node>> orElseGet(Supplier<ParseUnit<Node>> other);
}
