package magma.app.compile;

import magma.api.Tuple2;
import magma.api.collect.Iter;
import magma.api.option.Option;

public interface DivideState {
    Iter<String> query();

    DivideState advance();

    DivideState append(char c);

    boolean isLevel();

    DivideState enter();

    DivideState exit();

    boolean isShallow();

    Option<Tuple2<DivideState, Character>> pop();

    Option<Tuple2<DivideState, Character>> popAndAppendToTuple();

    Option<DivideState> popAndAppendToOption();

    char peek();

    boolean startsWith(String slice);
}
