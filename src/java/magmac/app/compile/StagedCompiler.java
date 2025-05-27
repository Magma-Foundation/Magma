package magmac.app.compile;

import magmac.api.result.Result;
import magmac.app.compile.error.CompileError;
import magmac.app.io.Location;
import magmac.app.stage.generate.Generator;
import magmac.app.stage.lexer.Lexer;
import magmac.app.stage.parse.Parser;

import java.util.Map;

public record StagedCompiler(Lexer lexer, Parser parser, Generator generator) implements Compiler {
    @Override
    public Result<Map<Location, String>, CompileError> compile(Map<Location, String> units) {
        return this.lexer.apply(units)
                .flatMapValue(trees -> this.parser.apply(trees))
                .flatMapValue(trees -> this.generator.apply(trees));
    }
}