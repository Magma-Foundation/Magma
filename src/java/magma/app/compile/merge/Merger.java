package magma.app.compile.merge;

import magma.api.Tuple2;
import magma.api.Tuple2Impl;
import magma.api.collect.list.Iterable;
import magma.api.collect.list.List;
import magma.app.compile.CompileState;

public interface Merger {
    static Tuple2<CompileState, String> generateAllFromTuple(CompileState state, List<String> elements, Merger merger) {
        return new Tuple2Impl<CompileState, String>(state, generateAll(elements, merger));
    }

    static String generateAll(Iterable<String> elements, Merger merger) {
        return elements.iter().foldWithInitial("", merger::merge);
    }

    String merge(String s, String s2);
}
