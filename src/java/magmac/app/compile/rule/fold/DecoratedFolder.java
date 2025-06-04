package magmac.app.compile.rule.fold;

import magmac.app.compile.rule.divide.DivideState;

/**
 * Folder decorator that ignores special characters inside
 * single quotes, double quotes and comments.
 */
public class DecoratedFolder implements Folder {
    private final Folder inner;

    public DecoratedFolder(Folder inner) {
        this.inner = inner;
    }

    @Override
    public DivideState fold(DivideState state, char c) {
        char last = state.last();

        if (state.inLineComment()) {
            state = state.append(c);
            if ('\n' == c) {
                state = state.endLineComment().advance();
            }
            return state.last(c);
        }

        if (state.inBlockComment()) {
            state = state.append(c);
            if ('*' == last && '/' == c) {
                state = state.endBlockComment().advance();
            }
            return state.last(c);
        }

        if (state.inSingle()) {
            return this.handleSingleQuote(state, c);
        }

        if (state.inDouble()) {
            return this.handleDoubleQuote(state, c);
        }

        if ('\'' == c) {
            state = state.append(c)
                    .startSingle()
                    .endEscape();
            return state.last(c);
        }

        if ('\"' == c) {
            state = state.append(c)
                    .startDouble()
                    .endEscape();
            return state.last(c);
        }

        if ('/' == last && '/' == c) {
            state = state.append(c).startLineComment();
            return state.last(c);
        }

        if ('/' == last && '*' == c) {
            state = state.append(c).startBlockComment();
            return state.last(c);
        }

        state = this.inner.fold(state, c);
        return state.last(c);
    }

    @Override
    public String createDelimiter() {
        return this.inner.createDelimiter();
    }

    /**
     * Handles characters inside a single quoted sequence.
     */
    private DivideState handleSingleQuote(DivideState state, char c) {
        state = state.append(c);
        if (state.escape()) {
            state = state.endEscape();
        } else if ('\\' == c) {
            state = state.startEscape();
        } else if ('\'' == c) {
            state = state.endSingle();
        }
        return state.last(c);
    }

    /**
     * Handles characters inside a double quoted sequence.
     * Escaped characters are skipped and the quote ends on a newline or
     * the matching double quote.
     */
    private DivideState handleDoubleQuote(DivideState state, char c) {
        state = state.append(c);
        if (state.escape()) {
            state = state.endEscape();
        } else if ('\\' == c) {
            state = state.startEscape();
        } else if ('\"' == c) {
            state = state.endDouble();
        }
        return state.last(c);
    }
}
