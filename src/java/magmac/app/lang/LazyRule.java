package magmac.app.lang;

import magmac.app.compile.rule.Rule;

/**
 * Proxy rule that can be assigned later to break dependency cycles.
 */
public interface LazyRule extends Rule {
    LazyRule set(Rule rule);
}
