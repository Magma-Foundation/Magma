package magmac.app.compile.rule.fold;

import magmac.app.compile.rule.divide.DivideState;

public class StatementFolder implements Folder {
    @Override
    public DivideState fold(DivideState state, char c) {
        var appended = state.append(c);
        if (';' == c && appended.isLevel()) {
            return appended.advance();
        }
        if ('}' == c && appended.isShallow()) {
            return appended.advance().exit();
        }
        if ('{' == c || '(' == c) {
            return appended.enter();
        }
        if ('}' == c || ')' == c) {
            return appended.exit();
        }
        return appended;
    }

    @Override
    public String createDelimiter() {
        return "";
    }
}
