package magmac;

import magmac.app.Application;
import magmac.app.CompileApplication;
import magmac.app.compile.lang.JavaRoots;
import magmac.app.io.PathSources;
import magmac.app.io.PathTargets;
import magmac.app.io.Sources;
import magmac.app.io.Targets;
import magmac.app.stage.Lexer;
import magmac.app.stage.RuleLexer;

import java.io.IOException;
import java.nio.file.Paths;

public class Main {
    public static void main() {
        Sources sources = new PathSources(Paths.get(".", "src", "java"));
        Targets targets = new PathTargets(Paths.get(".", "diagrams"));
        Lexer lexer = new RuleLexer(JavaRoots.createRule());
        Application application = new CompileApplication(sources, targets, lexer);
        application.run().ifPresent(error -> Main.handleError(error));
    }

    private static void handleError(IOException error) {
        //noinspection CallToPrintStackTrace
        error.printStackTrace();
    }
}
