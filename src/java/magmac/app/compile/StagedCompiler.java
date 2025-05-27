package magmac.app.compile;

import magmac.api.result.Result;
import magmac.app.compile.error.CompileError;
import magmac.app.compile.error.CompileResult;
import magmac.app.io.Location;
import magmac.app.stage.generate.Generator;
import magmac.app.stage.lexer.Lexer;
import magmac.app.stage.parse.Parser;

import java.util.Map;

public record StagedCompiler(Lexer lexer, Parser parser, Generator generator) implements Compiler {
    private Result<Map<Location, String>, CompileError> compile0(Map<Location, String> units) {
        return this.lexer.apply(units).result()
                .flatMapValue(trees -> this.parser.apply(trees).result())
                .flatMapValue(trees -> this.generator.apply(trees).result());
    }

    @Override
    public CompileResult<Map<Location, String>> compile(Map<Location, String> units) {
        return new CompileResult<>(this.compile0(units));
    }
}