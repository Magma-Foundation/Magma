package magmac.app.io;

import magmac.api.result.Result;

import java.io.IOException;
import java.util.function.Function;

/**
 * IO operation that may succeed with a value or fail with an {@link IOException}.
 */
public interface IOResult<T> {
    <R> IOResult<R> mapValue(Function<T, R> mapper);

    <R> IOResult<R> flatMapValue(Function<T, IOResult<R>> mapper);

    <R> Result<T, R> mapErr(Function<IOException, R> mapper);

    Result<T, IOException> result();
}
