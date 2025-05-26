package magmac.app;

import magmac.app.io.Location;
import magmac.app.io.Sources;
import magmac.app.io.Targets;
import magmac.app.stage.Generator;
import magmac.app.stage.Lexer;
import magmac.app.stage.Parser;
import magmac.app.stage.Roots;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public final class CompileApplication implements Application {
    private final Sources sources;
    private final Targets targets;
    private final Lexer lexer;
    private final Parser parser;
    private final Generator generator;

    public CompileApplication(Sources sources, Targets targets, Lexer lexer, Parser parser, Generator generator) {
        this.sources = sources;
        this.targets = targets;
        this.lexer = lexer;
        this.parser = parser;
        this.generator = generator;
    }

    @Override
    public Optional<IOException> run() {
        return this.sources.readAll().match(units -> {
            Roots lex = this.lexer.lexAll(units);
            Roots parsed = this.parser.parseAll(lex);
            Map<Location, String> outputs = this.generator.generateAll(parsed);
            return this.targets.writeAll(outputs);
        }, Optional::of);
    }

}