package magmac.app.stage.lexer;

import magmac.api.iter.collect.ResultCollector;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileResults;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.Rule;
import magmac.app.io.Location;
import magmac.app.io.sources.UnitSetCollector;
import magmac.app.stage.Unit;
import magmac.app.stage.UnitSet;

public class RuleLexer implements Lexer {
    private final Rule rootRule;

    public RuleLexer(Rule rootRule) {
        this.rootRule = rootRule;
    }

    private CompileResult<Unit<Node>> foldEntry(Unit<String> tuple) {
        Location location = tuple.left();
        String input = tuple.right();

        System.out.println("Lexing: " + location);
        return this.rootRule.lex(input).mapValue((Node root) -> new Unit<>(location, root));
    }

    @Override
    public CompileResult<UnitSet<Node>> apply(UnitSet<String> initial) {
        return CompileResults.fromResult(initial.iter()
                .map((Unit<String> entry) -> this.foldEntry(entry))
                .map((CompileResult<Unit<Node>> tuple2CompileResult) -> tuple2CompileResult.result())
                .collect(new ResultCollector<>(new UnitSetCollector<>())));
    }
}
