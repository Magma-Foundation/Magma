package magmac.app.stage;

import magmac.api.Tuple2;
import magmac.api.collect.Iters;
import magmac.api.result.Result;
import magmac.app.compile.lang.JavaRoots;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.io.Location;
import magmac.app.io.Source;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Lexer {
    public static Result<Map<Location, Node>, IOException> lexSources(Set<Source> sources) {
        return Iters.fromSet(sources).foldToResult(
                new HashMap<Location, Node>(),
                (nodes, source) -> lexSource(nodes, source)
        );
    }

    private static Result<Map<Location, Node>, IOException> lexSource(Map<Location, Node> nodes, Source source) {
        return lex(source).mapValue(compiled -> {
            nodes.put(compiled.left(), compiled.right());
            return nodes;
        });
    }

    private static Result<Tuple2<Location, Node>, IOException> lex(Source source) {
        return source.read().mapValue(input -> {
            Node root = JavaRoots.createRule()
                    .lex(input)
                    .toOptional()
                    .orElse(new MapNode());

            return new Tuple2<>(source.computeLocation(), root);
        });
    }
}
