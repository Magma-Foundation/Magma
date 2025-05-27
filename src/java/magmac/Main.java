package magmac;

import magmac.app.Application;
import magmac.app.CompileApplication;
import magmac.app.compile.lang.java.JavaRoots;
import magmac.app.compile.lang.plant.PlantUMLRoots;
import magmac.app.error.Error;
import magmac.app.io.PathSources;
import magmac.app.io.PathTargets;
import magmac.app.io.Sources;
import magmac.app.io.Targets;
import magmac.app.stage.AfterAll;
import magmac.app.stage.AfterPasser;
import magmac.app.stage.BeforePasser;
import magmac.app.stage.Lexer;
import magmac.app.stage.Parser;
import magmac.app.stage.RuleGenerator;
import magmac.app.stage.RuleLexer;
import magmac.app.stage.TreeParser;

import java.nio.file.Paths;

public final class Main {
    public static void main() {
        Sources sources = new PathSources(Paths.get(".", "src", "java"));
        Targets targets = new PathTargets(Paths.get(".", "diagrams"));
        Lexer lexer = new RuleLexer(JavaRoots.createRule());
        Parser parser = new TreeParser(new BeforePasser(), new AfterPasser(), new AfterAll());
        Application application = new CompileApplication(sources, targets, lexer, parser, new RuleGenerator(PlantUMLRoots.createRule()));
        application.run().ifPresent(error -> Main.handleError(error));
    }

    private static void handleError(Error error) {
        //noinspection CallToPrintStackTrace
        System.err.println(error.display());
    }
}
