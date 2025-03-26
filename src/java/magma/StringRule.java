package magma;

public record StringRule(String propertyKey) implements Rule {
    @Override
    public Result<String, CompileException> generate(Node node) {
        return node.find(propertyKey())
                .<Result<String, CompileException>>map(Ok::new)
                .orElseGet(() -> new Err<>(new CompileException("String '" + propertyKey() + "' not present", node.toString())));
    }
}