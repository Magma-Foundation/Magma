package magmac.app.compile;

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.Serializable;
import magmac.app.stage.generate.Generator;
import magmac.app.stage.lexer.Lexer;
import magmac.app.stage.parse.Parser;
import magmac.app.stage.unit.UnitSet;

import java.util.function.Function;

public final class StagedCompiler<T, R extends Serializable> implements Compiler {
    private final Lexer lexer;
    private final Parser<T, R> parser;
    private final Generator generator;
    private final Function<Node, CompileResult<T>> deserializer;

    public StagedCompiler(Lexer lexer, Function<Node, CompileResult<T>> deserializer, Parser<T, R> parser, Generator generator) {
        this.lexer = lexer;
        this.parser = parser;
        this.generator = generator;
        this.deserializer = deserializer;
    }

    @Override
    public CompileResult<UnitSet<String>> compile(UnitSet<String> units) {
        return this.lexer.apply(units)
                .flatMapValue((UnitSet<Node> roots) -> roots.mapValuesToResult(this.deserializer))
                .flatMapValue(this.parser::apply)
                .mapValue((UnitSet<R> roots) -> roots.mapValues(Serializable::serialize))
                .flatMapValue(this.generator::apply);
    }
}