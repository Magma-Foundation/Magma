package magma.app.compile.rule;

import magma.api.result.Result;
import magma.app.compile.CompileError;
import magma.app.compile.MapNode;
import magma.app.compile.ParseState;

public interface Rule {
    Result<MapNode, CompileError> parse(ParseState state, String input);

    Result<Output, CompileError> generate(MapNode node);
}
