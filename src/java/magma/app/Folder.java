package magma.app;

import magma.app.compile.DivideState;

import java.util.function.BiFunction;

interface Folder {
    DivideState apply(DivideState divideState, Character c);
}
