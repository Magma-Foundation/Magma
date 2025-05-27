package magmac.app.stage;

import magmac.api.Tuple2;
import magmac.api.collect.Iters;
import magmac.api.collect.collect.MapCollector;
import magmac.api.collect.collect.ResultCollector;
import magmac.api.result.Result;
import magmac.app.compile.CompileError;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.Rule;
import magmac.app.io.Location;

import java.util.Map;

public class RuleLexer implements Lexer {
    private final Rule rootRule;

    public RuleLexer(Rule rootRule) {
        this.rootRule = rootRule;
    }

    private Result<Map<Location, Node>, CompileError> lexAll0(Map<Location, String> values) {
        return Iters.fromMap(values)
                .map(entry -> this.getLocationNodeTuple2(entry))
                .collect(new ResultCollector<>(new MapCollector<>()));
    }

    private Result<Tuple2<Location, Node>, CompileError> getLocationNodeTuple2(Tuple2<Location, String> tuple) {
        Location location = tuple.left();
        String input = tuple.right();

        return this.rootRule
                .lex(input)
                .mapValue(root -> new Tuple2<>(location, root));
    }

    @Override
    public Result<Roots, CompileError> lexAll(Map<Location, String> values) {
        return this.lexAll0(values).mapValue(Roots::new);
    }
}
