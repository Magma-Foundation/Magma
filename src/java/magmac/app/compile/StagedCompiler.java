package magmac.app.compile;

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.stage.unit.UnitSet;
import magmac.app.stage.generate.Generator;
import magmac.app.stage.lexer.Lexer;
import magmac.app.stage.parse.Parser;

public record StagedCompiler(Lexer lexer, Parser parser, Generator generator) implements Compiler {
    @Override
    public CompileResult<UnitSet<String>> compile(UnitSet<String> units) {
        return this.lexer.apply(units)
                .flatMapValue((UnitSet<Node> trees) -> this.parser.apply(trees))
                .flatMapValue((UnitSet<Node> trees) -> this.generator.apply(trees));
    }
}