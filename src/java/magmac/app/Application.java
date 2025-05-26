package magmac.app;

import magmac.api.Tuple2;
import magmac.api.collect.Iters;
import magmac.api.result.Result;
import magmac.app.compile.lang.JavaRoots;
import magmac.app.compile.lang.PlantUMLRoots;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.io.Location;
import magmac.app.io.Source;
import magmac.app.io.Sources;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public final class Application {
    private final Sources sources;
    private final Targets targets;

    public Application(Sources sources, Targets targets) {
        this.sources = sources;
        this.targets = targets;
    }

    private static Result<Map<Source, Node>, IOException> compileSources(Set<Source> sources) {
        return Iters.fromSet(sources).foldToResult(
                new HashMap<Source, Node>(),
                (nodes, source) -> Application.lexSources(nodes, source)
        );
    }

    private static Result<Map<Source, Node>, IOException> lexSources(Map<Source, Node> nodes, Source source) {
        return Application.lexSource(source).mapValue(compiled -> {
            nodes.put(compiled.left(), compiled.right());
            return nodes;
        });
    }

    private static Result<Tuple2<Source, Node>, IOException> lexSource(Source source) {
        return source.read().mapValue(input -> {
            Node root = JavaRoots.createRule()
                    .lex(input)
                    .toOptional()
                    .orElse(new MapNode());

            return new Tuple2<>(source, root);
        });
    }

    private Optional<IOException> generateSegments(Tuple2<Location, Node> tuple) {
        String generated = PlantUMLRoots.createRule().generate(tuple.right())
                .toOptional()
                .orElse("");

        return this.targets.write(tuple.left(), "@startuml\nskinparam linetype ortho\n" +
                generated +
                "@enduml\n");
    }

    public Optional<IOException> run() {
        return this.sources().collect().match(units -> {
            return Application.compileSources(units).match(root -> {
                Tuple2<Location, Node> children = Compiler.compile(root);
                return this.generateSegments(children);
            }, Optional::of);
        }, Optional::of);
    }

    public Sources sources() {
        return this.sources;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (null == obj || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (Application) obj;
        return Objects.equals(this.sources, that.sources);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.sources);
    }

    @Override
    public String toString() {
        return "Application[" +
                "sources=" + this.sources + ']';
    }

}