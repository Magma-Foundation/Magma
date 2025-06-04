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

    /**
     * Peeks at the next character if available.
     *
     * @return the upcoming character or empty when the input is consumed
     */
    Option<Character> peek();

    boolean inSingle();

    /** Begin a single quoted sequence. */
    DivideState startSingle();

    /** Finish a single quoted sequence. */
    DivideState endSingle();

    boolean inDouble();

    /** Begin a double quoted sequence. */
    DivideState startDouble();

    /** Finish a double quoted sequence. */
    DivideState endDouble();

    boolean inLineComment();

    /** Start a line comment. */
    DivideState startLineComment();

    /** End a line comment. */
    DivideState endLineComment();

    boolean inBlockComment();

    /** Start a block comment. */
    DivideState startBlockComment();

    /** End a block comment. */
    DivideState endBlockComment();

    boolean escape();

    /** Mark the next character as escaped. */
    DivideState startEscape();

    /** Cancel escape state. */
    DivideState endEscape();

    char last();

    DivideState last(char c);
}
