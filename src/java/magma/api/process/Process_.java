package magma.api.process;

import magma.api.concurrent.InterruptedError;
import magma.api.result.Result;

public interface Process_ {
    Result<Integer, InterruptedError> waitFor();
}
