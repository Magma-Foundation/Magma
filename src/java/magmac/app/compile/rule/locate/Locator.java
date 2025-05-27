package magmac.app.compile.rule.locate;

import java.util.Optional;

public interface Locator {
    Optional<Integer> locate(String input, String infix);
}
