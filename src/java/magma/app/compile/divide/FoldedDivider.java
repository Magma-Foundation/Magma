package magma.app.compile.divide;

import jvm.api.collect.list.Lists;
import magma.api.Tuple2Impl;
import magma.api.collect.Iter;
import magma.app.compile.DivideState;
import magma.app.compile.ImmutableDivideState;
import magma.app.compile.fold.Folder;

public record FoldedDivider(Folder folder) implements Divider {
    @Override
    public Iter<String> divide(String input) {
        var current = (DivideState) new ImmutableDivideState(Lists.empty(), "", 0, input, 0);

        while (true) {
            var poppedTuple0 = current.pop().toTuple(new Tuple2Impl<DivideState, Character>(current, '\0'));
            if (!poppedTuple0.left()) {
                break;
            }

            var poppedTuple = poppedTuple0.right();
            var poppedState = poppedTuple.left();
            var popped = poppedTuple.right();

            current = this.folder.apply(poppedState, popped);
        }

        return current.advance().query();
    }
}