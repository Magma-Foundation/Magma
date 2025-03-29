package magma;

import java.util.List;

public interface DividingState {
    DividingState append(char c);

    boolean isLevel();

    DividingState exit();

    DividingState enter();

    DividingState advance();

    List<String> segments();
}
