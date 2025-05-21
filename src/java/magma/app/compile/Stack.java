package magma.app.compile;

import magma.api.collect.list.Iterable;
import magma.api.option.Option;
import magma.app.compile.define.Definition;

public interface Stack {
    Option<String> findLastStructureName();

    boolean isWithinLast(String name);

    boolean hasAnyStructureName(String base);

    Option<Definition> resolveValue(String name);

    Stack pushStructureName(String name);

    Stack defineAll(Iterable<Definition> definitions);

    Stack popStructureName();
}
