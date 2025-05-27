package magmac.app.stage.generate;

import magmac.api.Tuple2;
import magmac.api.collect.MapCollector;
import magmac.api.collect.ResultCollector;
import magmac.api.result.Result;
import magmac.app.compile.error.CompileError;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.rule.Rule;
import magmac.app.io.Location;
import magmac.app.stage.Roots;

import java.util.Map;

public class RuleGenerator implements Generator {
    private final Rule rootRule;

    public RuleGenerator(Rule rootRule) {
        this.rootRule = rootRule;
    }

    private Result<Map<Location, String>, CompileError> apply0(Roots roots) {
        return roots.iter()
                .map(entry -> this.rootRule.generate(entry.right()).result().mapValue(generated -> new Tuple2<>(entry.left(), generated)))
                .collect(new ResultCollector<>(new MapCollector<>()));
    }

    @Override
    public CompileResult<Map<Location, String>> apply(Roots initial) {
        return new CompileResult<>(this.apply0(initial));
    }
}