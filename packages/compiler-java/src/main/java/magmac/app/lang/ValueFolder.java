package magmac.app.lang;

import magmac.app.compile.rule.divide.DivideState;
import magmac.app.compile.rule.fold.Folder;

public class ValueFolder implements Folder {
    @Override
    public DivideState fold(DivideState state, char c) {
        if (',' == c && state.isLevel()) {
            return state.advance();
        }

        var appended = state.append(c);
        if ('-' == c) {
            if (state.peek().filter(n -> n == '>').isPresent()) {
                return state.popAndAppendToOption().orElse(state);
            }
        }

        if ('<' == c || '(' == c || '{' == c) {
            return appended.enter();
        }
        if ('>' == c || ')' == c || '}' == c) {
            return appended.exit();
        }
        return appended;
    }

    @Override
    public String createDelimiter() {
        return ", ";
    }
}
