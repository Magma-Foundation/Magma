package magma.app.compile.fold;

import magma.app.compile.DivideState;

public class TypeSeparatorFolder implements Folder {
    @Override
    public DivideState apply(DivideState state1, char c) {
        if (' ' == c && state1.isLevel()) {
            return state1.advance();
        }

        var appended = state1.append(c);
        if ('<' == c) {
            return appended.enter();
        }
        if ('>' == c) {
            return appended.exit();
        }
        return appended;
    }
}
