package magmac.app.compile.error.context;

/**
 * Additional information attached to a {@code CompileError} for context.
 */
public interface Context {
    /**
     * Formats this context as a human readable string.
     */
    String display();
}
