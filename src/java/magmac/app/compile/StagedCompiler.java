package magmac.app.compile;

import magmac.app.compile.error.CompileResult;
import magmac.app.lang.Serializable;
import magmac.app.lang.java.JavaLang;
import magmac.app.stage.generate.Generator;
import magmac.app.stage.parse.Parser;
import magmac.app.stage.unit.UnitSet;

public final class StagedCompiler<R extends Serializable> implements Compiler {
    private final Parser<JavaLang.Root, R> parser;
    private final Generator generator;

    public StagedCompiler(Parser<JavaLang.Root, R> parser, Generator generator) {
        this.parser = parser;
        this.generator = generator;
    }

    @Override
    public CompileResult<UnitSet<String>> parseAndGenerate(UnitSet<JavaLang.Root> units) {
        return this.parser.apply(units)
                .mapValue((UnitSet<R> roots) -> roots.mapValues(Serializable::serialize))
                .flatMapValue(this.generator::apply);
    }
}