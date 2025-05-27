package magmac.app.config;

import magmac.app.compile.Compiler;
import magmac.app.compile.StagedCompiler;
import magmac.app.compile.lang.java.JavaRoots;
import magmac.app.compile.lang.plant.PlantUMLRoots;
import magmac.app.stage.AfterPasser;
import magmac.app.stage.CreateDiagram;
import magmac.app.stage.Generator;
import magmac.app.stage.JavaToPlantUML;
import magmac.app.stage.Lexer;
import magmac.app.stage.Parser;
import magmac.app.stage.RuleGenerator;
import magmac.app.stage.RuleLexer;
import magmac.app.stage.TreeParser;

public class Config {
    public static Compiler createCompiler() {
        Lexer lexer = new RuleLexer(JavaRoots.createRule());
        Parser parser = new TreeParser(new JavaToPlantUML(), new AfterPasser(), new CreateDiagram());
        Generator generator = new RuleGenerator(PlantUMLRoots.createRule());
        return new StagedCompiler(lexer, parser, generator);
    }
}