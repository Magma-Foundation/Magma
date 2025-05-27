package magmac.app.error;

import magmac.app.Error;

public interface CompileError extends Error {
    int computeMaxDepth();

    String format(int depth);
}
