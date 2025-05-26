package magmac.app.compile;

import java.util.stream.Stream;

public interface DivideState {
    DivideState append(char c);

    DivideState advance();

    Stream<String> stream();
}
