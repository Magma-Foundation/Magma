package magma.app.compile.rule;

import magma.api.result.Result;
import magma.api.result.Tuple;
import magma.app.compile.CompileError;
import magma.app.compile.MapNode;
import magma.app.compile.ParseState;

public interface Rule {
    Result<MapNode, CompileError> parse(String input);

    Result<MapNode, CompileError> transform(ParseState state, MapNode input);

    Result<Tuple<String, String>, CompileError> generate(MapNode node);
}
