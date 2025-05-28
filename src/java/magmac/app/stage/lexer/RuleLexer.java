package magmac.app.stage.lexer;

import magmac.api.Tuple2;
import magmac.api.collect.map.Map;
import magmac.api.collect.map.MapCollector;
import magmac.api.iter.collect.ResultCollector;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.InlineCompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.Rule;
import magmac.app.io.Location;
import magmac.app.stage.MapUnitSet;
import magmac.app.stage.UnitSet;

public class RuleLexer implements Lexer {
    private final Rule rootRule;

    public RuleLexer(Rule rootRule) {
        this.rootRule = rootRule;
    }

    private CompileResult<Tuple2<Location, Node>> foldEntry(Tuple2<Location, String> tuple) {
        Location location = tuple.left();
        String input = tuple.right();

        System.out.println("Lexing: " + location);
        return this.rootRule.lex(input).mapValue((Node root) -> new Tuple2<>(location, root));
    }

    @Override
    public CompileResult<UnitSet<Node>> apply(Map<Location, String> initial) {
        return InlineCompileResult.fromResult(initial.iterEntries()
                .map((Tuple2<Location, String> entry) -> this.foldEntry(entry))
                .map((CompileResult<Tuple2<Location, Node>> tuple2CompileResult) -> tuple2CompileResult.result())
                .collect(new ResultCollector<>(new MapCollector<>()))
                .mapValue((Map<Location, Node> roots) -> new MapUnitSet(roots)));
    }
}
