package magmac.app.stage;

import magmac.api.Tuple2;
import magmac.api.collect.Iters;
import magmac.api.collect.collect.MapCollector;
import magmac.api.collect.collect.ResultCollector;
import magmac.api.result.Result;
import magmac.app.compile.error.CompileError;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.Rule;
import magmac.app.io.Location;

import java.util.Map;

public class RuleLexer implements Lexer {
    private final Rule rootRule;

    public RuleLexer(Rule rootRule) {
        this.rootRule = rootRule;
    }

    private Result<Tuple2<Location, Node>, CompileError> foldEntry(Tuple2<Location, String> tuple) {
        Location location = tuple.left();
        String input = tuple.right();

        return this.rootRule
                .lex(input)
                .mapValue(root -> new Tuple2<>(location, root));
    }

    @Override
    public Result<Roots, CompileError> lexAll(Map<Location, String> values) {
        return Iters.fromMap(values)
                .map(entry -> this.foldEntry(entry))
                .collect(new ResultCollector<>(new MapCollector<>()))
                .mapValue(Roots::new);
    }
}
