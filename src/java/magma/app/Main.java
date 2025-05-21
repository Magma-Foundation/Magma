package magma.app;

import jvm.api.io.Files;
import magma.api.collect.Iters;
import magma.api.io.Console;
import magma.api.io.IOError;
import magma.api.option.Option;

final class Main {
    public static void main() {
        var sourceDirectory = Files.get(".", "src", "java");
        var sources = new PathSources(sourceDirectory);
        var targets = new PathTargets(Files.get(".", "src", "ts"));
        Main.run(new Application(sources, targets))
                .map((IOError error) -> error.display())
                .ifPresent((String displayed) -> Console.printErrLn(displayed));
    }

    private static Option<IOError> run(Application application) {
        return Iters.fromArray(Platform.values())
                .map((Platform platform) -> application.runWith(platform))
                .flatMap(Iters::fromOption)
                .next();
    }
}