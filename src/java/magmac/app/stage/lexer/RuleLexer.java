package magmac.app.stage.lexer;

import magmac.api.Tuple2;
import magmac.api.collect.MapCollector;
import magmac.api.collect.ResultCollector;
import magmac.api.iter.Iters;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.InlineCompileResult;
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

    private CompileResult<Tuple2<Location, Node>> foldEntry(Tuple2<Location, String> tuple) {
        Location location = tuple.left();
        String input = tuple.right();

        return this.rootRule.lex(input).mapValue(root -> new Tuple2<>(location, root));
    }

    @Override
    public CompileResult<Roots> apply(Map<Location, String> initial) {
        return InlineCompileResult.fromResult(Iters.fromMap(initial)
                .map(entry -> this.foldEntry(entry))
                .map(CompileResult::result)
                .collect(new ResultCollector<>(new MapCollector<>()))
                .mapValue(MapRoots::new));
    }
}
