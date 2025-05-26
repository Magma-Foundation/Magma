package magmac.app;

import magmac.app.compile.node.Node;
import magmac.app.io.Location;
import magmac.app.io.Sources;
import magmac.app.io.Targets;
import magmac.app.stage.Generator;
import magmac.app.stage.Lexer;
import magmac.app.stage.Parser;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public final class CompileApplication implements Application {
    private final Sources sources;
    private final Targets targets;
    private final Lexer lexer;

    public CompileApplication(Sources sources, Targets targets, Lexer lexer) {
        this.sources = sources;
        this.targets = targets;
        this.lexer = lexer;
    }

    @Override
    public Optional<IOException> run() {
        return this.sources.readAll().match(units -> {
            Map<Location, Node> roots = this.lexer.lexAll(units);
            Map<Location, Node> parsed = Parser.parseAll(roots);
            Map<Location, String> outputs = Generator.generateAll(parsed);
            return this.targets.writeAll(outputs);
        }, Optional::of);
    }
}