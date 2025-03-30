package magma.compile.rule.divide;

import magma.collect.list.List_;

public interface DividingState {
    DividingState append(char c);

    boolean isLevel();

    DividingState exit();

    DividingState enter();

    DividingState advance();

    List_<String> segments();

    boolean isShallow();
}
