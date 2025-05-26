package magmac.app.stage;

import magmac.api.Tuple2;
import magmac.api.collect.Iters;
import magmac.api.collect.collect.MapCollector;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.Rule;
import magmac.app.io.Location;

import java.util.Map;

public class RuleGenerator implements Generator {
    private final Rule rootRule;

    public RuleGenerator(Rule rootRule) {
        this.rootRule = rootRule;
    }

    private String generate(Node value) {
        return this.rootRule.generate(value)
                .toOptional()
                .orElse("");
    }

    @Override
    public Map<Location, String> generateAll(Roots parsed) {
        return Iters.fromMap(parsed.roots())
                .map(entry -> new Tuple2<>(entry.left(), generate(entry.right())))
                .collect(new MapCollector<>());
    }
}