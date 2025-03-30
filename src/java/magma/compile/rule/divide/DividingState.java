package magma.compile.rule.divide;

import magma.collect.list.List_;
import magma.option.Option;
import magma.option.Tuple;

public interface DividingState {
    DividingState append(char c);

    boolean isLevel();

    DividingState exit();

    DividingState enter();

    DividingState advance();

    List_<String> segments();

    boolean isShallow();

    Option<Tuple<Character, DividingState>> popAndAppend();
}
