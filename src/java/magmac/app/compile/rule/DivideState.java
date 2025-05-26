package magmac.app.compile.rule;

import java.util.stream.Stream;

public interface DivideState {
    DivideState append(char c);

    DivideState advance();

    Stream<String> stream();
}
