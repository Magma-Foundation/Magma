package magma.compile;

public class GeneratingException extends Exception {
    public final String content;

    public GeneratingException(String message) {
        super(message);
        this.content = "";
    }

    public GeneratingException(String message, String content) {
        super(message);
        this.content = content;
    }

    public GeneratingException(String message, String content, Throwable cause) {
        super(message, cause);
        this.content = content;
    }
}
