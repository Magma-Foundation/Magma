package magmac.app.stage;

import magmac.api.Tuple2;
import magmac.api.collect.Iters;
import magmac.api.collect.collect.MapCollector;
import magmac.api.collect.collect.ResultCollector;
import magmac.api.result.Result;
import magmac.app.error.CompileError;
import magmac.app.compile.rule.Rule;
import magmac.app.io.Location;

import java.util.Map;

public class RuleGenerator implements Generator {
    private final Rule rootRule;

    public RuleGenerator(Rule rootRule) {
        this.rootRule = rootRule;
    }

    @Override
    public Result<Map<Location, String>, CompileError> generateAll(Roots parsed) {
        return Iters.fromMap(parsed.roots())
                .map(entry -> {
                    return this.rootRule.generate(entry.right()).mapValue(generated -> {
                        return new Tuple2<>(entry.left(), generated);
                    });
                })
                .collect(new ResultCollector<>(new MapCollector<>()));
    }
}