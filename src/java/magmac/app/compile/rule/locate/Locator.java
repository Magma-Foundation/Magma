package magmac.app.compile.rule.locate;

import magmac.api.Option;

public interface Locator {
    Option<Integer> locate(String input, String infix);
}
