package magma.app.compile.fold;

import magma.api.Tuple2;
import magma.api.text.Strings;
import magma.app.compile.DivideState;

public class OperatorFolder implements Folder {
    private final String infix;

    public OperatorFolder(String infix) {
        this.infix = infix;
    }

    @Override
    public DivideState apply(DivideState state, char c) {
        if (c == infix.charAt(0) && state.startsWith(Strings.sliceFrom(infix, 1))) {
            var length = Strings.length(infix) - 1;
            var counter = 0;
            var current = state;
            while (counter < length) {
                counter++;

                current = current.pop().map((Tuple2<DivideState, Character> tuple) -> tuple.left()).orElse(current);
            }
            return current.advance();
        }

        return state.append(c);
    }
}
