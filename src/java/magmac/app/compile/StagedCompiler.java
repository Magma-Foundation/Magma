package magmac.app.compile;

import magmac.api.result.Result;
import magmac.app.error.CompileError;
import magmac.app.io.Location;
import magmac.app.stage.Generator;
import magmac.app.stage.Lexer;
import magmac.app.stage.Parser;
import magmac.app.stage.Roots;

import java.util.Map;

public record StagedCompiler(Lexer lexer, Parser parser, Generator generator) implements Compiler {
    @Override
    public Result<Map<Location, String>, CompileError> compile(Map<Location, String> units) {
        return this.lexer().lexAll(units).flatMapValue(trees -> {
            Roots parsed = this.parser().parseAll(trees);
            return this.generator().generateAll(parsed);
        });
    }
}