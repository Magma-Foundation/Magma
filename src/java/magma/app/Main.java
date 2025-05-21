package magma.app;

import jvm.api.io.Files;
import magma.api.io.Console;
import magma.api.io.IOError;

public final class Main {
    public static void main() {
        var sourceDirectory = Files.get(".", "src", "java");
        var sources = new PathSources(sourceDirectory);
        var targets = new PathTargets(Files.get(".", "src", "ts"));
        Application.run(new Application(sources, targets))
                .findError()
                .map((IOError error) -> error.display())
                .ifPresent((String displayed) -> Console.printErrLn(displayed));
    }
}