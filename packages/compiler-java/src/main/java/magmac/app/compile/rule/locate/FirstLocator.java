package magmac.app.compile.rule.locate;

import magmac.api.None;
import magmac.api.Option;
import magmac.api.Some;

public class FirstLocator implements Locator {
    @Override
    public Option<Integer> locate(String input, String infix) {
        var index = input.indexOf(infix);
        if (-1 == index) {
            return new None<Integer>();
        }
        return new Some<>(index);
    }
}