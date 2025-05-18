package magma.app.compile;

import jvm.api.collect.list.Lists;
import magma.api.collect.Joiner;
import magma.api.collect.Query;
import magma.api.collect.list.List;
import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;
import magma.api.text.Strings;
import magma.app.compile.define.Definition;
import magma.app.compile.type.Type;
import magma.app.io.Source;

public record CompileState(
        List<Import> imports,
        String output,
        List<String> structureNames,
        int depth,
        List<Definition> definitions,
        Option<List<String>> maybeNamespace,
        List<Source> sources
) {
    public static CompileState createInitial() {
        return new CompileState(Lists.empty(), "", Lists.empty(), 0, Lists.empty(), new None<List<String>>(), Lists.empty());
    }

    public String getJoined(String otherOutput) {
        var imports = this.queryImports()
                .map((Import anImport) -> anImport.generate())
                .collect(new Joiner(""))
                .orElse("");

        return imports + this.output() + otherOutput;
    }

    public Query<Import> queryImports() {
        return this.imports().query();
    }

    public Query<Source> querySources() {
        return this.sources()
                .query();
    }

    public String createIndent() {
        return "\n" + "\t".repeat(this.depth());
    }

    public Option<String> findLastStructureName() {
        return this.structureNames().findLast();
    }

    public boolean isLastWithin(String name) {
        return this.structureNames.findLast()
                .filter((String anObject) -> Strings.equalsTo(name, anObject))
                .isPresent();
    }

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
                .filter((Import node) -> Strings.equalsTo(node.child(), child))
                .next()
                .isPresent()) {
            return this;
        }

        var importString = new Import(stringList, child);
        return new CompileState(this.imports.addLast(importString), this.output, this.structureNames, this.depth, this.definitions, this.maybeNamespace, this.sources);
    }

    public CompileState withNamespace(List<String> namespace) {
        return new CompileState(this.imports, this.output, this.structureNames, this.depth, this.definitions, new Some<List<String>>(namespace), this.sources);
    }

    public CompileState append(String element) {
        return new CompileState(this.imports, this.output + element, this.structureNames, this.depth, this.definitions, this.maybeNamespace, this.sources);
    }

    public CompileState pushStructureName(String name) {
        return new CompileState(this.imports, this.output, this.structureNames.addLast(name), this.depth, this.definitions, this.maybeNamespace, this.sources);
    }

    public CompileState enterDepth() {
        return new CompileState(this.imports, this.output, this.structureNames, this.depth + 1, this.definitions, this.maybeNamespace, this.sources);
    }

    public CompileState exitDepth() {
        return new CompileState(this.imports, this.output, this.structureNames, this.depth - 1, this.definitions, this.maybeNamespace, this.sources);
    }

    public CompileState defineAll(List<Definition> definitions) {
        return new CompileState(this.imports, this.output, this.structureNames, this.depth, this.definitions.addAll(definitions), this.maybeNamespace, this.sources);
    }

    public Option<Type> resolve(String name) {
        return this.definitions.queryReversed()
                .filter((Definition definition) -> Strings.equalsTo(definition.name(), name))
                .map((Definition definition1) -> definition1.type())
                .next();
    }

    public CompileState clearImports() {
        return new CompileState(Lists.empty(), this.output, this.structureNames, this.depth, this.definitions, this.maybeNamespace, this.sources);
    }

    public CompileState clearOutput() {
        return new CompileState(this.imports, "", this.structureNames, this.depth, this.definitions, this.maybeNamespace, this.sources);
    }

    public CompileState addSource(Source source) {
        return new CompileState(this.imports, this.output, this.structureNames, this.depth, this.definitions, this.maybeNamespace, this.sources.addLast(source));
    }

    private Option<Source> findSource(String name) {
        return this.sources.query()
                .filter((Source source) -> Strings.equalsTo(source.computeName(), name))
                .next();
    }

    public CompileState addResolvedImportFromCache(String base) {
        if (this.structureNames.query().anyMatch((String inner) -> Strings.equalsTo(inner, base))) {
            return this;
        }

        return this.findSource(base)
                .map((Source source) -> this.addResolvedImport(source.computeNamespace(), source.computeName()))
                .orElse(this);
    }

    public CompileState popStructureName() {
        return new CompileState(this.imports, this.output, this.structureNames.removeLast().orElse(this.structureNames), this.depth, this.definitions, this.maybeNamespace, this.sources);
    }
}
