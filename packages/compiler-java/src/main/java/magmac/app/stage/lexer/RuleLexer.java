package magmac.app.stage.lexer;

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileResultCollector;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.Rule;
import magmac.app.io.sources.UnitSetCollector;
import magmac.app.stage.unit.Unit;
import magmac.app.stage.unit.UnitSet;

public class RuleLexer implements Lexer {
    private final Rule rootRule;

    public RuleLexer(Rule rootRule) {
        this.rootRule = rootRule;
    }

    private CompileResult<Unit<Node>> foldEntry(Unit<String> unit) {
        System.out.println(unit.display());
        return unit.mapValue(this.rootRule::lex);
    }

    @Override
    public CompileResult<UnitSet<Node>> apply(UnitSet<String> initial) {
        return initial.iter()
                .map(this::foldEntry)
                .collect(new CompileResultCollector<>(new UnitSetCollector<>()));
    }
}
