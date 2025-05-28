package magmac.app.compile;

import magmac.app.compile.error.CompileResult;
import magmac.app.io.Location;
import magmac.app.stage.Roots;
import magmac.app.stage.generate.Generator;
import magmac.app.stage.lexer.Lexer;
import magmac.app.stage.parse.Parser;

import magmac.api.collect.map.Map;

public record StagedCompiler(Lexer lexer, Parser parser, Generator generator) implements Compiler {
    @Override
    public CompileResult<Map<Location, String>> compile(Map<Location, String> units) {
        return this.lexer.apply(units)
                .flatMapValue((Roots trees) -> this.parser.apply(trees))
                .flatMapValue((Roots trees) -> this.generator.apply(trees));
    }
}