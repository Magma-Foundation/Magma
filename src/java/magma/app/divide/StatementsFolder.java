package magma.app.divide;

import magma.app.compile.DivideState;

public class StatementsFolder implements Folder {
    @Override
    public DivideState apply(DivideState state1, char c) {
        var appended = state1.append(c);
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
}
