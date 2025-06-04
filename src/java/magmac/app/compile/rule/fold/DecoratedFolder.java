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
                state = state.inLineComment(false).advance();
            }
            return state.last(c);
        }

        if (state.inBlockComment()) {
            state = state.append(c);
            if ('*' == last && '/' == c) {
                state = state.inBlockComment(false).advance();
            }
            return state.last(c);
        }

        if (state.inSingle()) {
            state = state.append(c);
            if (state.escape()) {
                state = state.escape(false);
            } else if ('\\' == c) {
                state = state.escape(true);
            } else if ('\'' == c) {
                state = state.inSingle(false);
            }
            return state.last(c);
        }

        if (state.inDouble()) {
            state = state.append(c);
            if (state.escape()) {
                state = state.escape(false);
            } else if ('\\' == c) {
                state = state.escape(true);
            } else if ('\"' == c) {
                state = state.inDouble(false);
            }
            return state.last(c);
        }

        if ('\'' == c) {
            state = state.append(c)
                    .inSingle(true)
                    .escape(false);
            return state.last(c);
        }

        if ('\"' == c) {
            state = state.append(c)
                    .inDouble(true)
                    .escape(false);
            return state.last(c);
        }

        if ('/' == last && '/' == c) {
            state = state.append(c).inLineComment(true);
            return state.last(c);
        }

        if ('/' == last && '*' == c) {
            state = state.append(c).inBlockComment(true);
            return state.last(c);
        }

        state = this.inner.fold(state, c);
        return state.last(c);
    }

    @Override
    public String createDelimiter() {
        return this.inner.createDelimiter();
    }
}
