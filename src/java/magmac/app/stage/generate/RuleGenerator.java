package magmac.app.stage.generate;

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileResultCollector;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.Rule;
import magmac.app.io.sources.UnitSetCollector;
import magmac.app.stage.unit.Unit;
import magmac.app.stage.unit.UnitSet;

public class RuleGenerator implements Generator {
    private final Rule rootRule;

    public RuleGenerator(Rule rootRule) {
        this.rootRule = rootRule;
    }

    @Override
    public CompileResult<UnitSet<String>> apply(UnitSet<Node> initial) {
        return initial.iter()
                .map(this::generateEntry)
                .collect(new CompileResultCollector<>(new UnitSetCollector<>()));
    }

    private CompileResult<Unit<String>> generateEntry(Unit<Node> entry) {
        return entry.mapValue(this.rootRule::generate);
    }
}