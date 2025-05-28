package magmac.app.lang;

import magmac.app.compile.rule.divide.DivideState;
import magmac.app.compile.rule.fold.Folder;

public class InvocationFolder implements Folder {
    @Override
    public DivideState fold(DivideState state, char c) {
        DivideState appended = state.append(c);
        if ('(' == c && appended.isLevel()) {
            return appended.advance();
        }
        if ('(' == c) {
            return appended.enter();
        }
        if (')' == c) {
            return appended.exit();
        }
        return appended;
    }

    @Override
    public String createDelimiter() {
        return "";
    }
}
