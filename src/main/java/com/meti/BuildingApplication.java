package com.meti;

import com.meti.module.Module;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class BuildingApplication extends Application {

    public BuildingApplication(Module module) {
        super(module);
    }

    @Override
    protected void build(ArrayList<String> targets) throws ApplicationException {
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

            process.getInputStream().transferTo(System.out);
            process.getErrorStream().transferTo(System.err);
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            throw new ApplicationException(e);
        }
    }

}
