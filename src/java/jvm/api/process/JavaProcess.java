package jvm.api.process;

import jvm.api.concurrent.JavaInterruptedError;
import jvm.api.result.JavaResults;
import magma.api.concurrent.InterruptedError;
import magma.api.process.Process_;
import magma.api.result.Result;

public record JavaProcess(Process process) implements Process_ {
    @Override
    public Result<Integer, InterruptedError> waitFor() {
        return JavaResults
                .wrapSupplier(process()::waitFor)
                .mapErr(JavaInterruptedError::new);
    }
}