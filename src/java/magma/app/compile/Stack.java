package magma.app.compile;

import magma.api.collect.list.Iterable;
import magma.api.collect.list.List;
import magma.api.option.Option;
import magma.api.text.Strings;
import magma.app.compile.define.Definition;

public record Stack(List<String> structureNames, List<Definition> definitions) {
    public   Option<String> findLastStructureName() {
        return this.structureNames().findLast();
    }

    public boolean isWithinLast(String name) {
        return findLastStructureName()
                .filter((String anObject) -> Strings.equalsTo(name, anObject))
                .isPresent();
    }

    public  boolean hasAnyStructureName(String base) {
        return structureNames().iter().anyMatch((String inner) -> Strings.equalsTo(inner, base));
    }

    public  Option<Definition> resolveValue(String name) {
        return definitions().iterReversed()
                .filter((Definition definition) -> definition.isNamed(name))
                .next();
    }

    Stack pushStructureName(String name) {
        return new Stack(structureNames().addLast(name), definitions());
    }

    Stack defineAll(Iterable<Definition> definitions) {
        return new Stack(structureNames(), definitions().addAll(definitions));
    }

    Stack popStructureName() {
        return new Stack(structureNames().removeLast().orElse(structureNames()), definitions());
    }
}