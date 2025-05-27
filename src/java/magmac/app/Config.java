package magmac.app;

import magmac.app.compile.Compiler;
import magmac.app.compile.StagedCompiler;
import magmac.app.lang.JavaRoots;
import magmac.app.lang.PlantUMLRoots;
import magmac.app.lang.AfterPasser;
import magmac.app.lang.CreateDiagram;
import magmac.app.stage.generate.Generator;
import magmac.app.lang.JavaToPlantUML;
import magmac.app.stage.lexer.Lexer;
import magmac.app.stage.Parser;
import magmac.app.stage.generate.RuleGenerator;
import magmac.app.stage.lexer.RuleLexer;
import magmac.app.stage.parse.TreeParser;

public final class Config {
    public static Compiler createCompiler() {
        Lexer lexer = Config.createLexer();
        Parser parser = Config.createParser();
        Generator generator = Config.createGenerator();
        return new StagedCompiler(lexer, parser, generator);
    }

    private static Generator createGenerator() {
        return new RuleGenerator(PlantUMLRoots.createRule());
    }

    private static Lexer createLexer() {
        return new RuleLexer(JavaRoots.createRule());
    }

    private static Parser createParser() {
        return new TreeParser(new JavaToPlantUML(), new AfterPasser(), new CreateDiagram());
    }
}