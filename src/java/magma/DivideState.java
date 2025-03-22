package magma;

import java.util.List;
import java.util.Optional;

public interface DivideState {
    Optional<DivideState> popAndAppend();

    DivideState exit();

    DivideState enter();

    Optional<Character> pop();

    DivideState append(char maybeEscape);

    boolean isLevel();

    boolean isShallow();

    DivideState advance();

    List<String> segments();
}
