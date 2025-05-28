package magmac.app.stage.generate;

import magmac.api.Tuple2;
import magmac.api.collect.map.MapCollector;
import magmac.api.iter.collect.ResultCollector;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.InlineCompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.Rule;
import magmac.app.io.Location;
import magmac.app.stage.UnitSet;

import magmac.api.collect.map.Map;

public class RuleGenerator implements Generator {
    private final Rule rootRule;

    public RuleGenerator(Rule rootRule) {
        this.rootRule = rootRule;
    }

    @Override
    public CompileResult<Map<Location, String>> apply(UnitSet<Node> initial) {
        return InlineCompileResult.fromResult(initial.iter().map(unit -> unit.tuple())
                .map((Tuple2<Location, Node> entry) -> this.rootRule.generate(entry.right()).result().mapValue((String generated) -> new Tuple2<>(entry.left(), generated)))
                .collect(new ResultCollector<>(new MapCollector<>())));
    }
}