package magmac.app.stage;

import magmac.api.Tuple2;
import magmac.api.collect.Iters;
import magmac.api.collect.MapCollector;
import magmac.api.collect.ResultCollector;
import magmac.api.result.Result;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.Rule;
import magmac.app.io.Location;
import magmac.app.io.Source;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class RuleLexer implements Lexer {
    private final Rule rootRule;

    public RuleLexer(Rule rootRule) {
        this.rootRule = rootRule;
    }

    @Override
    public Result<Map<Location, Node>, IOException> lexAll(Set<Source> sources) {
        return Iters.fromSet(sources)
                .map(source -> this.lexSource(source))
                .collect(new ResultCollector<>(new MapCollector<>()));
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
