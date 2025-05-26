package magmac.app.stage;

import magmac.api.Tuple2;
import magmac.api.collect.Iters;
import magmac.api.result.Result;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.Rule;
import magmac.app.io.Location;
import magmac.app.io.Source;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RuleLexer implements Lexer {
    private final Rule rootRule;

    public RuleLexer(Rule rootRule) {
        this.rootRule = rootRule;
    }

    @Override
    public Result<Map<Location, Node>, IOException> lexAll(Set<Source> sources) {
        return Iters.fromSet(sources).foldToResult(
                new HashMap<Location, Node>(),
                (nodes, source) -> this.foldSource(nodes, source)
        );
    }

    private Result<Map<Location, Node>, IOException> foldSource(Map<Location, Node> nodes, Source source) {
        return this.lexSource(source).mapValue(compiled -> {
            nodes.put(compiled.left(), compiled.right());
            return nodes;
        });
    }

    private Result<Tuple2<Location, Node>, IOException> lexSource(Source source) {
        return source.read().mapValue(input -> {
            Node root = this.rootRule
                    .lex(input)
                    .toOptional()
                    .orElse(new MapNode());

            return new Tuple2<>(source.computeLocation(), root);
        });
    }
}
