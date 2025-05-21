package magma.app.compile;

import jvm.api.collect.list.Lists;
import magma.api.collect.list.Iterable;
import magma.api.collect.list.List;
import magma.api.option.Option;
import magma.api.text.Strings;
import magma.app.compile.define.Definition;

public record ImmutableStack(List<String> structureNames, List<Definition> definitions) implements Stack {
    static Stack createEmpty() {
        return new ImmutableStack(Lists.empty(), Lists.empty());
    }

    @Override
    public Option<String> findLastStructureName() {
        return this.structureNames().findLast();
    }

    @Override
    public boolean isWithinLast(String name) {
        return this.findLastStructureName()
                .filter((String anObject) -> Strings.equalsTo(name, anObject))
                .isPresent();
    }

    @Override
    public boolean hasAnyStructureName(String base) {
        return this.structureNames().iter().anyMatch((String inner) -> Strings.equalsTo(inner, base));
    }

    @Override
    public Option<Definition> resolveValue(String name) {
        return this.definitions().iterReversed()
                .filter((Definition definition) -> definition.isNamed(name))
                .next();
    }

    @Override
    public Stack pushStructureName(String name) {
        return new ImmutableStack(this.structureNames().addLast(name), this.definitions());
    }

    @Override
    public Stack defineAll(Iterable<Definition> definitions) {
        return new ImmutableStack(this.structureNames(), this.definitions().addAll(definitions));
    }

    @Override
    public Stack popStructureName() {
        return new ImmutableStack(this.structureNames().removeLast().orElse(this.structureNames()), this.definitions());
    }
}