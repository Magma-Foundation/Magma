package magmac.app.compile.rule.divide;

import magmac.api.Tuple2;

import java.util.Optional;
import java.util.stream.Stream;

public interface DivideState {
    DivideState append(char c);

    DivideState advance();

    Stream<String> stream();

    boolean isLevel();

    DivideState enter();

    DivideState exit();

    boolean isShallow();

    Optional<Tuple2<DivideState, Character>> pop();
}
