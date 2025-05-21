package magma.app.compile;

import magma.api.collect.list.Iterable;
import magma.api.collect.list.List;
import magma.api.option.Option;
import magma.api.text.Strings;
import magma.app.compile.define.Definition;

public record Stack(List<String> structureNames, List<Definition> definitions) {
    public Option<String> findLastStructureName() {
        return this.structureNames().findLast();
    }

    public boolean isWithinLast(String name) {
        return this.findLastStructureName()
                .filter((String anObject) -> Strings.equalsTo(name, anObject))
                .isPresent();
    }

    public boolean hasAnyStructureName(String base) {
        return this.structureNames().iter().anyMatch((String inner) -> Strings.equalsTo(inner, base));
    }

    public Option<Definition> resolveValue(String name) {
        return this.definitions().iterReversed()
                .filter((Definition definition) -> definition.isNamed(name))
                .next();
    }

    public Stack pushStructureName(String name) {
        return new Stack(this.structureNames().addLast(name), this.definitions());
    }

    public Stack defineAll(Iterable<Definition> definitions) {
        return new Stack(this.structureNames(), this.definitions().addAll(definitions));
    }

    public Stack popStructureName() {
        return new Stack(this.structureNames().removeLast().orElse(this.structureNames()), this.definitions());
    }
}