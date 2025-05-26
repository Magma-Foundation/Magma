package magmac.app.stage;

import magmac.api.result.Result;
import magmac.app.compile.node.Node;
import magmac.app.io.Location;
import magmac.app.io.Source;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public interface Lexer {
    Result<Map<Location, Node>, IOException> lexAll(Set<Source> sources);
}
