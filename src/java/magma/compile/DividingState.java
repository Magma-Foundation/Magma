package magma.compile;

import magma.collect.list.List_;

public interface DividingState {
    DividingState append(char c);

    boolean isLevel();

    DividingState exit();

    DividingState enter();

    DividingState advance();

    List_<String> segments();
}
