package magmac.app.compile.rule.locate;

import java.util.Optional;

public class FirstLocator implements Locator {
    @Override
    public Optional<Integer> locate(String input, String infix) {
        int index = input.indexOf(infix);
        return -1 == index
                ? Optional.empty()
                : Optional.of(index);
    }
}