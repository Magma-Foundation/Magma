package com.meti.app;

import com.meti.app.module.Module;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

public final class BuildingApplication extends Application {

    public BuildingApplication(Module module) {
        super(module);
    }

    @Override
    protected void build(Collection<String> targets) throws ApplicationException {
        var targetString = String.join(" ", targets);
        try {
            Out.resolveChild("CMakeLists.txt").ensureAsFile()
                    .writeAsString("project (" +
                                   AppName +
                                   ")\nadd_executable(" +
                                   AppName +
                                   " " +
                                   targetString +
                                   ")");
        } catch (IOException e) {
            throw new ApplicationException(e);
        }

        var outDirectory = Out.toAbsolutePath().asString();
        var workingDirectory = new File(outDirectory);

        if (!Out.resolveChild("CMakeCache.txt").exists()) {
            execute(workingDirectory, List.of("cmake", outDirectory));
        }

        execute(workingDirectory, List.of("cmake", "--build", outDirectory));
    }

    private void execute(File workingDirectory, List<String> command) throws ApplicationException {
        try {
            var process = new ProcessBuilder(command)
                    .directory(workingDirectory)
                    .start();

            var output = new ByteArrayOutputStream();
            var stream = new ByteArrayOutputStream();

            process.getInputStream().transferTo(output);
            process.getErrorStream().transferTo(stream);
            var exitCode = process.waitFor();

            var errorMessage = stream.toString();
            if (!errorMessage.isBlank()) {
                throw new ApplicationException("Failed to build native code with exit code " + exitCode + ": " + errorMessage);
            }

            System.out.println(output);
        } catch (IOException | InterruptedException e) {
            throw new ApplicationException(e);
        }
    }

}
