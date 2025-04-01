package magma.process;

import magma.io.ExceptionalIOError;
import magma.result.Result;
import jvm.result.Results;

import java.nio.file.Path;
import java.util.List;

public class Processes {
    public static Result<Process, ExceptionalIOError> startProcess(List<String> command, Path directory) {
        ProcessBuilder builder = new ProcessBuilder(command)
                .directory(directory.toFile())
                .inheritIO();

        return Results.wrapSupplier(builder::start).mapErr(ExceptionalIOError::new);
    }
}