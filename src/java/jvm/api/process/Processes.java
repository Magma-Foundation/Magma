package jvm.api.process;

import jvm.api.collect.Lists;
import jvm.api.io.JavaIOError;
import jvm.api.result.JavaResults;
import magma.api.collect.List_;
import magma.api.io.IOError;
import magma.api.process.Process_;
import magma.api.result.Result;

import java.nio.file.Path;

public class Processes {
    public static Result<Process_, IOError> start(List_<String> command, Path workingDirectory) {
        ProcessBuilder builder = new ProcessBuilder(Lists.toNative(command))
                .directory(workingDirectory.toFile())
                .inheritIO();

        return JavaResults.wrapSupplier(builder::start)
                .<Process_>mapValue(JavaProcess::new)
                .mapErr(JavaIOError::new);
    }
}
