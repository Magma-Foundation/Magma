package magma;

public record StringRule(String propertyKey) implements Rule {
    @Override
    public Result<String, CompileError> generate(Node node) {
        return node.find(propertyKey())
                .<Result<String, CompileError>>map(Ok::new)
                .orElseGet(() -> new Err<>(new CompileError("String '" + propertyKey() + "' not present", node.toString())));
    }
}