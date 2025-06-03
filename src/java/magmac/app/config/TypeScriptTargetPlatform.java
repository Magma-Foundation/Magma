package magmac.app.config;

import magmac.app.Application;
import magmac.app.CompileApplication;
import magmac.app.compile.Compiler;
import magmac.app.compile.StagedCompiler;
import magmac.app.compile.rule.Rule;
import magmac.app.io.targets.PathTargets;
import magmac.app.io.targets.Targets;
import magmac.app.lang.web.TypescriptLang;
import magmac.app.lang.java.JavaLang;
import magmac.app.lang.web.TypescriptRules;
import magmac.app.stage.generate.Generator;
import magmac.app.stage.generate.RuleGenerator;
import magmac.app.stage.parse.Parser;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class TypeScriptTargetPlatform implements TargetPlatform {

    @Override
    public Path createTargetPath() {
        return Paths.get(".", "src", "web");
    }

    @Override
    public String createExtension() {
        return "ts";
    }

    @Override
    public Rule createRule() {
        return TypescriptRules.createRule();
    }

    @Override
    public Compiler createCompiler() {
        Parser<JavaLang.JavaRoot, TypescriptLang.TypescriptRoot> parser = new JavaTypescriptParser();
        Generator generator = new RuleGenerator(this.createRule());
        return new StagedCompiler<TypescriptLang.TypescriptRoot>(parser, generator);
    }

    @Override
    public Application createApplication() {
        Path targetPath = this.createTargetPath();
        String extension = this.createExtension();
        Targets targets = new PathTargets(targetPath, extension);
        return new CompileApplication<>(this.createCompiler(), targets);
    }
}