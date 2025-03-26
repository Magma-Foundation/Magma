package magma;

public interface Rule {
    Result<String, CompileException> generate(Node node);
}
