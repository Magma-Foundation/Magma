package magmac.app.lang;

import magmac.app.compile.rule.Rule;

public interface LazyRule extends Rule {
    LazyRule set(Rule rule);
}
