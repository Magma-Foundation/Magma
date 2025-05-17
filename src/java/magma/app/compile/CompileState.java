package magma.app.compile;

import jvm.api.collect.list.Lists;
import jvm.api.text.Strings;
import magma.api.Type;
import magma.api.collect.list.List;
import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;
import magma.app.compile.define.Definition;
import magma.app.io.Location;
import magma.app.io.Platform;
import magma.app.io.Source;

import java.util.function.Function;

public record CompileState(
        List<Import> imports,
        String output,
        List<String> structureNames,
        int depth,
        List<Definition> definitions,
        Option<Location> maybeLocation,
        List<Source> sources,
        Platform platform
) {
    public static CompileState createInitial() {
        return new CompileState(Lists.empty(), "", Lists.empty(), 0, Lists.empty(), new None<Location>(), Lists.empty(), Platform.Magma);
    }

    public boolean isLastWithin(final String name) {
        return this.structureNames.findLast()
                .filter((String anObject) -> Strings.equalsTo(name, anObject))
                .isPresent();
    }

    private CompileState addResolvedImport(final List<String> oldParent, final String child) {
        final var namespace = this.maybeLocation
                .map((Location location) -> location.namespace())
                .orElse(Lists.empty());

        var newParent = oldParent;
        if (Platform.TypeScript == this.platform) {
            if (namespace.isEmpty()) {
                newParent = newParent.addFirst(".");
            }

            var i = 0;
            final var size = namespace.size();
            while (i < size) {
                newParent = newParent.addFirst("..");
                i++;
            }
        }

        if (this.imports
                .query()
                .filter((Import node) -> Strings.equalsTo(node.child(), child))
                .next()
                .isPresent()) {
            return this;
        }

        final var importString = new Import(newParent, child);
        return new CompileState(this.imports.addLast(importString), this.output, this.structureNames, this.depth, this.definitions, this.maybeLocation, this.sources, this.platform);
    }

    public CompileState withLocation(final Location namespace) {
        return new CompileState(this.imports, this.output, this.structureNames, this.depth, this.definitions, new Some<Location>(namespace), this.sources, this.platform);
    }

    public CompileState append(final String element) {
        return new CompileState(this.imports, this.output + element, this.structureNames, this.depth, this.definitions, this.maybeLocation, this.sources, this.platform);
    }

    public CompileState pushStructureName(final String name) {
        return new CompileState(this.imports, this.output, this.structureNames.addLast(name), this.depth, this.definitions, this.maybeLocation, this.sources, this.platform);
    }

    public CompileState enterDepth() {
        return new CompileState(this.imports, this.output, this.structureNames, this.depth + 1, this.definitions, this.maybeLocation, this.sources, this.platform);
    }

    public CompileState exitDepth() {
        return new CompileState(this.imports, this.output, this.structureNames, this.depth - 1, this.definitions, this.maybeLocation, this.sources, this.platform);
    }

    public CompileState defineAll(final List<Definition> definitions) {
        return new CompileState(this.imports, this.output, this.structureNames, this.depth, this.definitions.addAll(definitions), this.maybeLocation, this.sources, this.platform);
    }

    public Option<Type> resolve(final String name) {
        return this.definitions.queryReversed()
                .filter((Definition definition) -> Strings.equalsTo(definition.name(), name))
                .map((Definition definition1) -> definition1.type())
                .next();
    }

    public CompileState clearImports() {
        return new CompileState(Lists.empty(), this.output, this.structureNames, this.depth, this.definitions, this.maybeLocation, this.sources, this.platform);
    }

    public CompileState clearOutput() {
        return new CompileState(this.imports, "", this.structureNames, this.depth, this.definitions, this.maybeLocation, this.sources, this.platform);
    }

    public CompileState addSource(final Source source) {
        return new CompileState(this.imports, this.output, this.structureNames, this.depth, this.definitions, this.maybeLocation, this.sources.addLast(source), this.platform);
    }

    private Option<Source> findSource(final String name) {
        return this.sources.query()
                .filter((Source source) -> Strings.equalsTo(source.computeName(), name))
                .next();
    }

    public CompileState addResolvedImportFromCache(final String base) {
        if (this.structureNames.query().anyMatch((String inner) -> Strings.equalsTo(inner, base))) {
            return this;
        }

        return this.findSource(base)
                .map((Source source) -> this.addResolvedImport(source.computeNamespace(), source.computeName()))
                .orElse(this);
    }

    public CompileState popStructureName() {
        return new CompileState(this.imports, this.output, this.structureNames.removeLast().orElse(this.structureNames), this.depth, this.definitions, this.maybeLocation, this.sources, this.platform);
    }

    public CompileState mapLocation(final Function<Location, Location> mapper) {
        return new CompileState(this.imports, this.output, this.structureNames, this.depth, this.definitions, this.maybeLocation.map(mapper), this.sources, this.platform);
    }

    public CompileState withPlatform(final Platform platform) {
        return new CompileState(this.imports, this.output, this.structureNames, this.depth, this.definitions, this.maybeLocation, this.sources, platform);
    }
}
