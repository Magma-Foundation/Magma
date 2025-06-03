package magmac.app.lang;

import magmac.app.compile.rule.divide.DivideState;
import magmac.app.compile.rule.fold.Folder;

public class BlockFolder implements Folder {
    @Override
    public DivideState fold(DivideState state, char c) {
        var appended = state.append(c);
        if ('{' == c) {
            return appended.advance();
        }
        return appended;
    }

    @Override
    public String createDelimiter() {
        return "";
    }
}
