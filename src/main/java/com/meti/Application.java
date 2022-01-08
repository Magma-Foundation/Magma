package com.meti;

import com.meti.io.NIOPath;
import com.meti.module.Module;
import com.meti.source.Source;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.meti.io.NIOPath.Root;

public record Application(Module module) {
    public static final NIOPath Out = Root.resolveChild("out");
    private static final String AppName = "Application";

    void run() throws IOException, InterruptedException {
        var sources = module.listSources();
        var targets = new ArrayList<String>();
        for (var source : sources) {
            targets.add(compile(source));
        }

        var targetString = String.join(" ", targets);
        Out.resolveChild("CMakeLists.txt").ensureAsFile()
                .writeAsString("project (" +
                               AppName +
                               ")\nadd_executable(" +
                               AppName +
                               " " +
                               targetString +
                               ")");

        var outDirectory = Out.toAbsolutePath().asString();
        var workingDirectory = new File(outDirectory);

        if (!Out.resolveChild("CMakeCache.txt").exists()) {
            execute(workingDirectory, List.of("cmake", outDirectory));
        }

        execute(workingDirectory, List.of("cmake", "--build", outDirectory));
    }

    private void execute(File workingDirectory, List<String> command) throws IOException, InterruptedException {
        var process = new ProcessBuilder(command)
                .directory(workingDirectory)
                .start();

        process.getInputStream().transferTo(System.out);
        process.getErrorStream().transferTo(System.err);
        process.waitFor();
    }

    private static String compile(Source source) throws IOException {
        var name = source.computeName();

        var input = source.read();
        var output = new Compiler(input).compile();

        Out.ensureAsDirectory();
        var nioPath = source.computePackage()
                .reduce(Out, NIOPath::resolveChild, (previous, next) -> next)
                .resolveChild(name + ".c")
                .ensureAsFile()
                .writeAsString(output);
        return Out.relativize(nioPath).asString();
    }
}
