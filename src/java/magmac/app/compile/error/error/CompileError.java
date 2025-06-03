package magmac.app.compile.error.error;

import magmac.api.collect.list.List;
import magmac.api.error.Error;

public interface CompileError extends Error {
    int computeMaxDepth();

    String format(int depth, List<Integer> indices);
}
