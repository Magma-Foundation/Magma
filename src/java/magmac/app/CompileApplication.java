package magmac.app;

import magmac.api.Tuple2;
import magmac.api.collect.Iters;
import magmac.api.collect.MapCollector;
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
            Map<Location, Node> roots = this.lexer.lexAll(units);
            Map<Location, Node> parsed = this.parser.parseAll(roots);
            Map<Location, String> outputs = this.generateAll(parsed);
            return this.targets.writeAll(outputs);
        }, Optional::of);
    }

    private Map<Location, String> generateAll(Map<Location, Node> parsed) {
        return Iters.fromMap(parsed)
                .map(entry -> new Tuple2<>(entry.left(), this.generator.generate(entry.right())))
                .collect(new MapCollector<>());
    }
}