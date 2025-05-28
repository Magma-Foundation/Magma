package magmac.app.config;

import magmac.app.compile.Compiler;
import magmac.app.compile.StagedCompiler;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.Rule;
import magmac.app.lang.TypeScriptAfterPasser;
import magmac.app.lang.TypescriptLang;
import magmac.app.lang.java.root.JavaRoot;
import magmac.app.stage.Passer;
import magmac.app.stage.after.AfterAll;
import magmac.app.stage.after.EmptyAfterAll;
import magmac.app.stage.generate.Generator;
import magmac.app.stage.generate.RuleGenerator;
import magmac.app.stage.lexer.Lexer;
import magmac.app.stage.parse.Parser;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class TypeScriptTargetPlatform implements TargetPlatform {
    @Override
    public Passer createAfterChild() {
        return new TypeScriptAfterPasser();
    }

    @Override
    public Path createTargetPath() {
        return Paths.get(".", "src", "web");
    }

    @Override
    public AfterAll createAfterAll() {
        return new EmptyAfterAll();
    }

    @Override
    public String createExtension() {
        return "ts";
    }

    @Override
    public Rule createRule() {
        return TypescriptLang.createRule();
    }

    @Override
    public Compiler createCompiler(Lexer lexer) {
        Parser<JavaRoot, TypescriptRoot> parser = new JavaTypescriptParser();
        Generator generator = new RuleGenerator(this.createRule());
        return new StagedCompiler<JavaRoot, TypescriptRoot>(lexer, (Node node) -> JavaRoot.deserialize(node), parser, generator);
    }
}