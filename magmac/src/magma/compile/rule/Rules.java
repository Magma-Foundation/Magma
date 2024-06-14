package magma.compile.rule;

import java.util.Optional;

public class Rules {
    public static Optional<Integer> wrapIndex(int index) {
        return index == -1 ? Optional.empty() : Optional.of(index);
    }

}
