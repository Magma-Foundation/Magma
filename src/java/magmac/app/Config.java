package magmac.app;

import magmac.app.compile.Compiler;
import magmac.app.compile.StagedCompiler;
import magmac.app.compile.rule.Rule;
import magmac.app.lang.JavaRoots;
import magmac.app.lang.AfterPasser;
import magmac.app.lang.MergeDiagram;
import magmac.app.stage.generate.Generator;
import magmac.app.lang.FlattenJava;
import magmac.app.stage.lexer.Lexer;
import magmac.app.stage.parse.Parser;
import magmac.app.stage.generate.RuleGenerator;
import magmac.app.stage.lexer.RuleLexer;
import magmac.app.stage.parse.TreeParser;

public final class Config {
    public static Compiler createCompiler(Rule rootRule) {
        Lexer lexer = Config.createLexer();
        Parser parser = Config.createParser();
        Generator generator = new RuleGenerator(rootRule);
        return new StagedCompiler(lexer, parser, generator);
    }

    public static Lexer createLexer() {
        return new RuleLexer(JavaRoots.createRule());
    }

    public static Parser createParser() {
        return new TreeParser(new FlattenJava(), new AfterPasser(), new MergeDiagram());
    }
}