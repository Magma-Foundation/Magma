package magmac.app.stage.lexer;

import magmac.api.Tuple2;
import magmac.api.iter.Iters;
import magmac.api.collect.MapCollector;
import magmac.api.collect.ResultCollector;
import magmac.api.result.Result;
import magmac.app.compile.error.CompileError;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.Rule;
import magmac.app.io.Location;
import magmac.app.stage.MapRoots;
import magmac.app.stage.Roots;

import java.util.Map;

public class RuleLexer implements Lexer {
    private final Rule rootRule;

    public RuleLexer(Rule rootRule) {
        this.rootRule = rootRule;
    }

    private Result<Tuple2<Location, Node>, CompileError> foldEntry(Tuple2<Location, String> tuple) {
        Location location = tuple.left();
        String input = tuple.right();

        return this.rootRule.lex(input).result()
                .mapValue(root -> new Tuple2<>(location, root));
    }

    private Result<Roots, CompileError> apply0(Map<Location, String> values) {
        return Iters.fromMap(values)
                .map(entry -> this.foldEntry(entry))
                .collect(new ResultCollector<>(new MapCollector<>()))
                .mapValue(MapRoots::new);
    }

    @Override
    public CompileResult<Roots> apply(Map<Location, String> initial) {
        return new CompileResult<>(this.apply0(initial));
    }
}
