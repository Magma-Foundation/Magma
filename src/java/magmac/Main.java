package magmac;

import magmac.api.iter.Iters;
import magmac.app.ApplicationBuilder;
import magmac.api.error.Error;
import magmac.app.config.PlantUMLTargetPlatform;
import magmac.app.config.TypeScriptTargetPlatform;
import magmac.app.io.sources.PathSources;
import magmac.app.io.sources.Sources;

import java.nio.file.Paths;

final class Main {
    public static void main() {
        Sources sources = new PathSources(Paths.get(".", "src", "java"));

        Iters.fromValues(new PlantUMLTargetPlatform(), new TypeScriptTargetPlatform())
                .map(platform -> ApplicationBuilder.run(platform, sources))
                .flatMap(option -> Iters.fromOption(option))
                .next()
                .ifPresent(error -> Main.handleError(error));
    }

    private static void handleError(Error error) {
        System.err.println(error.display());
    }
}
