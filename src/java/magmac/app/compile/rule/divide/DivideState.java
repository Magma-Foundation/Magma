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

    char peek();

    boolean inSingle();

    DivideState inSingle(boolean inSingle);

    boolean inDouble();

    DivideState inDouble(boolean inDouble);

    boolean inLineComment();

    DivideState inLineComment(boolean inLineComment);

    boolean inBlockComment();

    DivideState inBlockComment(boolean inBlockComment);

    boolean escape();

    DivideState escape(boolean escape);

    char last();

    DivideState last(char c);
}
