package magma.app.compile.fold;

import magma.app.compile.DivideState;

public record DelimitedFolder(char delimiter) implements Folder {
    @Override
    public DivideState apply(DivideState state, char c) {
        if (this.delimiter() == c) {
            return state.advance();
        }
        return state.append(c);
    }
}