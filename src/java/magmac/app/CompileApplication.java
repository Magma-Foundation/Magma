package magmac.app;

import magmac.app.error.ApplicationError;
import magmac.app.error.Error;
import magmac.app.error.ThrowableError;
import magmac.app.io.Location;
import magmac.app.io.Sources;
import magmac.app.io.Targets;
import magmac.app.stage.Generator;
import magmac.app.stage.Lexer;
import magmac.app.stage.Parser;
import magmac.app.stage.Roots;

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
    public Optional<Error> run() {
        return this.sources.readAll()
                .mapErr(ThrowableError::new)
                .mapErr(ApplicationError::new)
                .match(units -> this.compileAndWrite(units), Optional::of);
    }

    private Optional<Error> compileAndWrite(Map<Location, String> units) {
        return this.lexer.lexAll(units).mapErr(ApplicationError::new).match(lex -> {
            Roots parsed = this.parser.parseAll(lex);
            return this.generator.generateAll(parsed)
                    .mapErr(ApplicationError::new)
                    .match(outputs -> this.targets.writeAll(outputs).map(ThrowableError::new).map(ApplicationError::new), err -> Optional.of(err));
        }, Optional::of);
    }
}