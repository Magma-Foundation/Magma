package magmac;

import magmac.app.CompileApplication;
import magmac.app.compile.lang.JavaRoots;
import magmac.app.io.PathTargets;
import magmac.app.io.PathSources;
import magmac.app.io.Sources;
import magmac.app.stage.RuleLexer;

import java.io.IOException;
import java.nio.file.Paths;

public class Main {
    public static void main() {
        Sources sources = new PathSources(Paths.get(".", "src", "java"));
        new CompileApplication(sources, new PathTargets(Paths.get(".", "diagrams")), new RuleLexer(JavaRoots.createRule())).run().ifPresent(error -> Main.handleError(error));
    }

    private static void handleError(IOException error) {
        //noinspection CallToPrintStackTrace
        error.printStackTrace();
    }
}
