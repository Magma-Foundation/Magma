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


public record ImmutableCompileState(
        Platform platform,
        Option<Location> findCurrentLocation,
        List<Source> sources,
        List<Import> imports,
        List<String> structureNames,
        String structures,
        String functions,
        List<Definition> definitions,
        int depth,
        List<String> definedTypes) implements CompileState {
    public static CompileState createInitial() {
        return new ImmutableCompileState(Platform.Magma,
                new None<Location>(),
                Lists.empty(),
                Lists.empty(),
                Lists.empty(),
                "",
                "",
                Lists.empty(),
                0,
                Lists.empty());
    }

    @Override
    public boolean hasLastStructureNameOf(final String name) {
        return this.structureNames.findLast()
                .filter((String anObject) -> Strings.equalsTo(name, anObject))
                .isPresent();
    }

    @Override
    public CompileState withLocation(final Location namespace) {
        return new ImmutableCompileState(this.platform, new Some<Location>(namespace), this.sources, this.imports, this.structureNames, this.structures, this.functions, this.definitions, this.depth, this.definedTypes);
    }

    @Override
    public CompileState append(final String element) {
        return new ImmutableCompileState(this.platform, this.findCurrentLocation, this.sources, this.imports, this.structureNames, this.structures + element, this.functions, this.definitions, this.depth, this.definedTypes);
    }

    @Override
    public CompileState pushStructureName(final String name) {
        return new ImmutableCompileState(this.platform, this.findCurrentLocation, this.sources, this.imports, this.structureNames.addLast(name), this.structures, this.functions, this.definitions, this.depth, this.definedTypes);
    }

    @Override
    public CompileState enterDepth() {
        return new ImmutableCompileState(this.platform, this.findCurrentLocation, this.sources, this.imports, this.structureNames, this.structures, this.functions, this.definitions, this.depth + 1, this.definedTypes);
    }

    @Override
    public CompileState exitDepth() {
        return new ImmutableCompileState(this.platform, this.findCurrentLocation, this.sources, this.imports, this.structureNames, this.structures, this.functions, this.definitions, this.depth - 1, this.definedTypes);
    }

    @Override
    public CompileState defineAll(final List<Definition> definitions) {
        return new ImmutableCompileState(this.platform, this.findCurrentLocation, this.sources, this.imports, this.structureNames, this.structures, this.functions, this.definitions.addAll(definitions), this.depth, this.definedTypes);
    }

    @Override
    public Option<Type> resolve(final String name) {
        return this.definitions.queryReversed()
                .filter((Definition definition) -> Strings.equalsTo(definition.name(), name))
                .map((Definition definition1) -> definition1.type())
                .next();
    }

    @Override
    public CompileState clearImports() {
        return new ImmutableCompileState(this.platform, this.findCurrentLocation, this.sources, Lists.empty(), this.structureNames, this.structures, this.functions, this.definitions, this.depth, this.definedTypes);
    }

    @Override
    public CompileState clearGenerated() {
        return new ImmutableCompileState(
                this.platform,
                this.findCurrentLocation,
                this.sources,
                this.imports,
                this.structureNames,
                "",
                "",
                this.definitions,
                this.depth, this.definedTypes);
    }

    @Override
    public CompileState addSource(final Source source) {
        return new ImmutableCompileState(this.platform, this.findCurrentLocation, this.sources.addLast(source), this.imports, this.structureNames, this.structures, this.functions, this.definitions, this.depth, this.definedTypes);
    }

    @Override
    public CompileState addResolvedImportFromCache(final String base) {
        if (this.structureNames.query().anyMatch((String inner) -> Strings.equalsTo(inner, base))) {
            return this;
        }

        return this.findSource(base)
                .map((Source source) -> this.addResolvedImportWithNamespace(source.computeNamespace(), source.computeName()))
                .orElse(this);
    }

    @Override
    public CompileState popStructureName() {
        return new ImmutableCompileState(this.platform, this.findCurrentLocation, this.sources, this.imports, this.structureNames.removeLast().orElse(this.structureNames), this.structures, this.functions, this.definitions, this.depth, this.definedTypes);
    }

    @Override
    public CompileState mapLocation(final Function<Location, Location> mapper) {
        return new ImmutableCompileState(this.platform, this.findCurrentLocation.map(mapper), this.sources, this.imports, this.structureNames, this.structures, this.functions, this.definitions, this.depth, this.definedTypes);
    }

    @Override
    public CompileState withPlatform(final Platform platform) {
        return new ImmutableCompileState(platform, this.findCurrentLocation, this.sources, this.imports, this.structureNames, this.structures, this.functions, this.definitions, this.depth, this.definedTypes);
    }

    @Override
    public CompileState addFunction(final String function) {
        return new ImmutableCompileState(this.platform, this.findCurrentLocation, this.sources, this.imports, this.structureNames, this.structures, this.functions + function, this.definitions, this.depth, this.definedTypes);
    }

    @Override
    public List<String> findDefinedTypes() {
        return this.definedTypes;
    }

    @Override
    public CompileState defineType(final String name) {
        return new ImmutableCompileState(this.platform, this.findCurrentLocation, this.sources, this.imports, this.structureNames, this.structures, this.functions, this.definitions, this.depth, this.definedTypes.addLast(name));
    }

    @Override
    public CompileState clearDefinedTypes() {
        return new ImmutableCompileState(this.platform, this.findCurrentLocation, this.sources, this.imports, this.structureNames, this.structures, this.functions, this.definitions, this.depth, Lists.empty());
    }

    @Override
    public String join() {
        return this.structures + this.functions;
    }

    @Override
    public CompileState addResolvedImportWithNamespace(final List<String> oldParent, final String child) {
        final var namespace = this.findCurrentLocation
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
        return new ImmutableCompileState(this.platform, this.findCurrentLocation, this.sources, this.imports.addLast(importString), this.structureNames, this.structures, this.functions, this.definitions, this.depth, this.definedTypes);
    }

    @Override
    public Option<Source> findSource(final String name) {
        return this.sources.query()
                .filter((Source source) -> Strings.equalsTo(source.computeName(), name))
                .next();
    }

    @Override
    public boolean isPlatform(final Platform platform) {
        return platform == this.platform();
    }

    @Override
    public String createIndent() {
        final var indent = this.depth();
        return "\n" + "\t".repeat(indent);
    }

    @Override
    public String functionName() {
        return "temp";
    }

    @Override
    public Option<String> findLastStructureName() {
        return this.structureNames().findLast();
    }
}
