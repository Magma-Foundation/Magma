package magmac;

import jvm.io.Console;
import magmac.api.iter.Iters;
import magmac.app.ApplicationBuilder;
import magmac.api.error.Error;
import magmac.app.config.PlantUMLTargetPlatform;
import magmac.app.config.TargetPlatform;
import magmac.app.config.TypeScriptTargetPlatform;
import magmac.app.io.sources.PathSources;
import magmac.app.io.sources.Sources;

import java.nio.file.Paths;

final class Main {
    public static void main() {
        Sources sources = new PathSources(Paths.get(".", "src", "java"));

        Iters.fromValues(new PlantUMLTargetPlatform(), new TypeScriptTargetPlatform())
                .map((TargetPlatform platform) -> ApplicationBuilder.run(platform, sources))
                .flatMap(Iters::fromOption)
                .next()
                .ifPresent((Error error) -> Console.handleError(error.display()));
    }
}
