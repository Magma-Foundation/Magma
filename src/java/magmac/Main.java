package magmac;

import magmac.app.Application;
import magmac.app.CompileApplication;
import magmac.app.compile.Compiler;
import magmac.app.Error;
import magmac.app.config.Config;
import magmac.app.io.PathSources;
import magmac.app.io.PathTargets;
import magmac.app.io.Sources;
import magmac.app.io.Targets;

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
