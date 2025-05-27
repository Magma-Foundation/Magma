package magmac.app.compile.error;

import magmac.api.error.Error;

public interface CompileError extends Error {
    int computeMaxDepth();

    String format(int depth);
}
