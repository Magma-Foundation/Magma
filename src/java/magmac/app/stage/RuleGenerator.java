package magmac.app.stage;

import magmac.app.compile.node.Node;
import magmac.app.compile.rule.Rule;

public class RuleGenerator implements Generator {
    private final Rule rootRule;

    public RuleGenerator(Rule rootRule) {
        this.rootRule = rootRule;
    }

    @Override
    public String generate(Node value) {
        return this.rootRule.generate(value)
                .toOptional()
                .orElse("");
    }
}