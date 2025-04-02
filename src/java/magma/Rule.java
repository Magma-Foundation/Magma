package magma;

import magma.result.Result;

public interface Rule {
    Result<Node, CompileException> parse(String input);

    Result<String, CompileException> generate(Node input);
}
