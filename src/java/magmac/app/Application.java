package magmac.app;

import jvm.io.SafeFiles;
import magmac.api.Tuple2;
import magmac.api.collect.Iterators;
import magmac.api.result.Result;
import magmac.app.compile.lang.JavaRoots;
import magmac.app.compile.lang.PlantUMLRoots;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.io.Source;
import magmac.app.io.Sources;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public record Application(Sources sources) {
    private static Optional<IOException> generateSegments(Node node) {
        String generated = PlantUMLRoots.createRule().generate(node).toOptional().orElse("");
        Path target = Paths.get(".", "diagram.puml");
        String csq = "@startuml\nskinparam linetype ortho\n" +
                generated +
                "@enduml\n";

        return SafeFiles.writeString(target, csq);
    }

    private static Result<Map<Source, Node>, IOException> compileSources(Set<Source> sources) {
        return Iterators.fromSet(sources).foldToResult(
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

    public Optional<IOException> run() {
        return this.sources().collect().match(units -> {
            return Application.compileSources(units).match(root -> {
                Node children = Compiler.compile(root);
                return Application.generateSegments(children);
            }, Optional::of);
        }, Optional::of);
    }
}