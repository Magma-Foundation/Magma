package magmac.app.compile.rule.divide;

import magmac.api.Tuple2;

import magmac.api.Option;
import magmac.api.iter.Iter;

public interface DivideState {
    DivideState append(char c);

    DivideState advance();

    Iter<String> iter();

    boolean isLevel();

    DivideState enter();

    DivideState exit();

    boolean isShallow();

    Option<Tuple2<DivideState, Character>> pop();

    Option<Tuple2<DivideState, Character>> popAndAppendToTuple();

    Option<DivideState> popAndAppendToOption();
}
