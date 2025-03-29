package jvm.process;

import jvm.collect.list.Lists;
import jvm.io.JavaIOError;
import jvm.io.Paths;
import jvm.result.Results;
import magma.JavaInterruptedError;
import magma.collect.list.List_;
import magma.io.Path_;
import magma.option.Option;
import magma.option.Some;
import magma.process.ProcessError;

public class Processes {
    public static Option<ProcessError> executeCommand(List_<String> command, Path_ workingDirectory) {
        ProcessBuilder builder = new ProcessBuilder(Lists.toNative(command))
                .directory(Paths.toNative(workingDirectory).toFile())
                .inheritIO();

        return Results.wrapSupplier(builder::start)
                .mapErr(JavaIOError::new)
                .mapErr(ProcessError::new)
                .match(process -> {
                    return Results.wrapRunnable(process::waitFor)
                            .map(JavaInterruptedError::new)
                            .map(ProcessError::new);
                }, Some::new);
    }
}