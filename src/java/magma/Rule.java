package magma;

import magma.result.Result;

public interface Rule {
    Result<String, CompileError> generate(MapNode node);

    Result<MapNode, CompileError> parse(String input);
}
