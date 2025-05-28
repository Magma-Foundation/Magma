package magmac;

import magmac.api.Option;
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
                .flatMap((Option<Error> option) -> Iters.fromOption(option))
                .next()
                .ifPresent((Error error) -> Main.handleError(error));
    }

    private static void handleError(Error error) {
        System.err.println(error.display());
    }
}
