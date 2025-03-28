package jvm.api.process;

import jvm.api.collect.Lists;
import jvm.api.io.JavaFiles;
import jvm.api.io.JavaIOError;
import jvm.api.result.JavaResults;
import magma.api.collect.List_;
import magma.api.io.IOError;
import magma.api.io.Path_;
import magma.api.process.Process_;
import magma.api.result.Result;

public class Processes {
    public static Result<Process_, IOError> start(List_<String> command, Path_ workingDirectory) {
        ProcessBuilder builder = new ProcessBuilder(Lists.toNative(command))
                .directory(JavaFiles.unwrap(workingDirectory).toFile())
                .inheritIO();

        return JavaResults.wrapSupplier(builder::start)
                .<Process_>mapValue(JavaProcess::new)
                .mapErr(JavaIOError::new);
    }
}
