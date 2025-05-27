package magmac.app.compile.rule.locate;

import magmac.api.None;
import magmac.api.Option;
import magmac.api.Some;

public class FirstLocator implements Locator {
    @Override
    public Option<Integer> locate(String input, String infix) {
        int index = input.indexOf(infix);
        return -1 == index ? new None<Integer>() : new Some<>(index);
    }
}