package magma;

public interface Rule {
    Result<String, CompileError> generate(Node node);
}
