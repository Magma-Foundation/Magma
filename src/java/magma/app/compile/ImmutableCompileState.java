package magma.app.compile;

import jvm.api.collect.list.Lists;
import magma.api.collect.Joiner;
import magma.api.collect.Query;
import magma.api.collect.list.List;
import magma.api.option.Option;
import magma.api.option.Some;
import magma.api.text.Strings;
import magma.app.compile.define.Definition;
import magma.app.io.Source;

public record ImmutableCompileState(
        List<Import> imports,
        String output,
        List<String> structureNames,
        int depth,
        List<Definition> definitions,
        Option<List<String>> maybeNamespace,
        List<Source> sources
) implements CompileState {
    @Override
    public String join(String otherOutput) {
        var joinedImports = this.queryImports()
                .map((Import anImport) -> anImport.generate())
                .collect(new Joiner(""))
                .orElse("");

        return joinedImports + this.output + otherOutput;
    }

    @Override
    public Query<Source> querySources() {
        return this.sources.query();
    }

    @Override
    public String createIndent() {
        return "\n" + "\t".repeat(this.depth);
    }

    @Override
    public Option<String> findLastStructureName() {
        return this.structureNames.findLast();
    }

    @Override
    public boolean isLastWithin(String name) {
        return this.structureNames.findLast()
                .filter((String anObject) -> Strings.equalsTo(name, anObject))
                .isPresent();
    }

    @Override
    public CompileState addResolvedImport(List<String> parent, String child) {
        var parent1 = parent;
        var namespace = this.maybeNamespace.orElse(Lists.empty());
        if (namespace.isEmpty()) {
            parent1 = parent1.addFirst(".");
        }

        var i = 0;
        var size = namespace.size();
        while (i < size) {
            parent1 = parent1.addFirst("..");
            i++;
        }

        var stringList = parent1.addLast(child);
        if (this.imports
                .query()
                .filter((Import node) -> node.hasSameChild(child))
                .next()
                .isPresent()) {
            return this;
        }

        var importString = new Import(stringList, child);
        return new ImmutableCompileState(this.imports.addLast(importString), this.output, this.structureNames, this.depth, this.definitions, this.maybeNamespace, this.sources);
    }

    @Override
    public CompileState withNamespace(List<String> namespace) {
        return new ImmutableCompileState(this.imports, this.output, this.structureNames, this.depth, this.definitions, new Some<List<String>>(namespace), this.sources);
    }

    @Override
    public CompileState append(String element) {
        return new ImmutableCompileState(this.imports, this.output + element, this.structureNames, this.depth, this.definitions, this.maybeNamespace, this.sources);
    }

    @Override
    public CompileState pushStructureName(String name) {
        return new ImmutableCompileState(this.imports, this.output, this.structureNames.addLast(name), this.depth, this.definitions, this.maybeNamespace, this.sources);
    }

    @Override
    public CompileState enterDepth() {
        return new ImmutableCompileState(this.imports, this.output, this.structureNames, this.depth + 1, this.definitions, this.maybeNamespace, this.sources);
    }

    @Override
    public CompileState exitDepth() {
        return new ImmutableCompileState(this.imports, this.output, this.structureNames, this.depth - 1, this.definitions, this.maybeNamespace, this.sources);
    }

    @Override
    public CompileState defineAll(List<Definition> definitions) {
        return new ImmutableCompileState(this.imports, this.output, this.structureNames, this.depth, this.definitions.addAll(definitions), this.maybeNamespace, this.sources);
    }

    @Override
    public Option<Definition> resolve(String name) {
        return this.definitions.queryReversed()
                .filter((Definition definition) -> definition.isNamed(name))
                .next();
    }

    @Override
    public CompileState clearImports() {
        return new ImmutableCompileState(Lists.empty(), this.output, this.structureNames, this.depth, this.definitions, this.maybeNamespace, this.sources);
    }

    @Override
    public CompileState clearOutput() {
        return new ImmutableCompileState(this.imports, "", this.structureNames, this.depth, this.definitions, this.maybeNamespace, this.sources);
    }

    @Override
    public CompileState addSource(Source source) {
        return new ImmutableCompileState(this.imports, this.output, this.structureNames, this.depth, this.definitions, this.maybeNamespace, this.sources.addLast(source));
    }

    @Override
    public Option<Source> findSource(String name) {
        return this.sources.query()
                .filter((Source source) -> Strings.equalsTo(source.computeName(), name))
                .next();
    }

    @Override
    public CompileState addResolvedImportFromCache(String base) {
        if (this.structureNames.query().anyMatch((String inner) -> Strings.equalsTo(inner, base))) {
            return this;
        }

        return this.findSource(base)
                .map((Source source) -> this.addResolvedImport(source.computeNamespace(), source.computeName()))
                .orElse(this);
    }

    @Override
    public CompileState popStructureName() {
        return new ImmutableCompileState(this.imports, this.output, this.structureNames.removeLast().orElse(this.structureNames), this.depth, this.definitions, this.maybeNamespace, this.sources);
    }

    @Override
    public Query<Import> queryImports() {
        return this.imports.query();
    }
}
