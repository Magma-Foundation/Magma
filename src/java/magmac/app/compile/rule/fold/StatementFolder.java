package magmac.app.compile.rule.fold;

import magmac.app.compile.rule.divide.DivideState;

public class StatementFolder implements Folder {
    @Override
    public DivideState fold(DivideState state, char c) {
        DivideState appended = state.append(c);
        if (';' == c && appended.isLevel()) {
            return appended.advance();
        }
        if ('{' == c) {
            return appended.enter();
        }
        if ('}' == c) {
            return appended.exit();
        }
        return appended;
    }
}
