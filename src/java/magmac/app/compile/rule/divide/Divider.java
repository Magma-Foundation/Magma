package magmac.app.compile.rule.divide;

import magmac.api.iter.Iter;

public interface Divider {
    Iter<String> divide(String input);
}
