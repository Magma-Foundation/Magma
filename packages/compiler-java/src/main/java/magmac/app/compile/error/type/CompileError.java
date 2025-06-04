package magmac.app.compile.error.type;

import magmac.api.collect.list.List;
import magmac.api.error.Error;

/**
 * Represents a problem encountered during compilation.
 */
public interface CompileError extends Error {
    /**
     * Computes the maximum indentation depth when formatting.
     */
    int computeMaxDepth();

    /**
     * Formats this error with an indentation depth and index stack.
     */
    String format(int depth, List<Integer> indices);
}
