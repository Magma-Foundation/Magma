package magmac.app.stage;

import magmac.app.compile.error.CompileResult;

/**
 * One step in the compiler pipeline transforming a value of type {@code T} into {@code R}.
 */
public interface Stage<T, R> {
    /**
     * Executes this stage using {@code initial} as input.
     */
    CompileResult<R> apply(T initial);
}
