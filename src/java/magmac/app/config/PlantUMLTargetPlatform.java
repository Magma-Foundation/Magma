package magmac.app.config;

import magmac.app.compile.Compiler;
import magmac.app.compile.StagedCompiler;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.Rule;
import magmac.app.lang.MergeDiagram;
import magmac.app.lang.PlantUMLAfterPasser;
import magmac.app.lang.PlantUMLLang;
import magmac.app.lang.java.node.JavaRoot;
import magmac.app.stage.Passer;
import magmac.app.stage.after.AfterAll;
import magmac.app.stage.generate.Generator;
import magmac.app.stage.generate.RuleGenerator;
import magmac.app.stage.lexer.Lexer;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class PlantUMLTargetPlatform implements TargetPlatform {

    @Override
    public Passer createAfterChild() {
        return new PlantUMLAfterPasser();
    }

    @Override
    public Path createTargetPath() {
        return Paths.get(".", "diagrams");
    }

    @Override
    public AfterAll createAfterAll() {
        return new MergeDiagram();
    }

    @Override
    public String createExtension() {
        return "puml";
    }

    @Override
    public Rule createRule() {
        return PlantUMLLang.createRule();
    }

    @Override
    public Compiler createCompiler(Lexer lexer) {
        Generator generator = new RuleGenerator(this.createRule());
        return new StagedCompiler<JavaRoot, PlantUMLRoot>(lexer, (Node node) -> JavaRoot.deserialize(node), new JavaPlantUMLParser(), generator);
    }
}