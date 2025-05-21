package magma.app.compile.fold;

import magma.app.compile.DivideState;

public interface Folder {
    DivideState apply(DivideState divideState, char c);
}
