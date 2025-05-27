package magmac;

import magmac.app.Application;
import magmac.app.CompileApplication;
import magmac.app.compile.Compiler;
import magmac.app.Error;
import magmac.app.Config;
import magmac.app.io.sources.PathSources;
import magmac.app.io.targets.PathTargets;
import magmac.app.io.sources.Sources;
import magmac.app.io.targets.Targets;

import java.nio.file.Paths;

final class Main {
    public static void main() {
        Sources sources = new PathSources(Paths.get(".", "src", "java"));
        Targets targets = new PathTargets(Paths.get(".", "diagrams"));
        Compiler compiler = Config.createCompiler();
        Application application = new CompileApplication(sources, compiler, targets);
        application.run().ifPresent(error -> Main.handleError(error));
    }

    private static void handleError(Error error) {
        //noinspection CallToPrintStackTrace
        System.err.println(error.display());
    }
}
