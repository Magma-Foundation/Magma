package magma.compile.rule.locate;

import magma.option.Option;

public interface Locator {
    Option<Integer> locate(String input, String infix);
}
