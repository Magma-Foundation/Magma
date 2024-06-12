package magma.api;

public class Results {
    public static <T, E extends Throwable> T unwrap(Result<T, E> result) throws E {
        var value = result.findValue();
        if (value.isPresent()) return value.get();

        var err = result.findErr();
        if (err.isPresent()) throw err.get();

        throw new RuntimeException("No value or err present?");
    }
}
