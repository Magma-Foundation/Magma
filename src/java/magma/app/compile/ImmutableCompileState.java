package magma.app.compile;

import jvm.api.collect.list.Lists;
import magma.api.collect.Iter;
import magma.api.collect.Joiner;
import magma.api.collect.list.List;
import magma.api.option.Option;
import magma.api.option.Some;
import magma.api.text.Strings;
import magma.app.Location;
import magma.app.Platform;
import magma.app.compile.define.Definition;
import magma.app.io.Source;

public record ImmutableCompileState(
        List<Import> imports,
        String output,
        List<String> structureNames,
        int depth,
        List<Definition> definitions,
        Option<magma.app.Location> maybeLocation,
        List<Source> sources,
        Platform platform,
        List<Dependency> dependencies) implements CompileState {
    @Override
    public String join(String otherOutput) {
        var joinedImports = this.queryImports()
                .map((Import anImport) -> anImport.generate())
                .collect(new Joiner(""))
                .orElse("");

        return joinedImports + this.output + otherOutput;
    }

    @Override
    public Iter<Source> querySources() {
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
    public CompileState addResolvedImport(Location location) {
        if (Platform.PlantUML == this.platform) {
            var name = this.maybeLocation.map(Location::name).orElse("");
            var dependency = new Dependency(name, location.name());
            if (!this.dependencies.contains(dependency)) {
                return new ImmutableCompileState(
                        this.imports,
                        this.output,
                        this.structureNames,
                        this.depth,
                        this.definitions,
                        this.maybeLocation,
                        this.sources,
                        this.platform,
                        this.dependencies.addLast(dependency)
                );
            }
        }

        var requestedNamespace = location.namespace();
        var requestedChild = location.name();

        var thisNamespace = this.maybeLocation
                .map(Location::namespace)
                .orElse(Lists.empty());

        if (thisNamespace.isEmpty()) {
            requestedNamespace = requestedNamespace.addFirst(".");
        }

        var i = 0;
        var size = thisNamespace.size();
        while (i < size) {
            requestedNamespace = requestedNamespace.addFirst("..");
            i++;
        }

        var stringList = requestedNamespace.addLast(requestedChild);
        if (doesImportExistAlready(requestedChild)) {
            return this;
        }

        var importString = new Import(stringList, requestedChild);
        return new ImmutableCompileState(this.imports.addLast(importString), this.output, this.structureNames, this.depth, this.definitions, this.maybeLocation, this.sources, this.platform, this.dependencies);
    }

    private boolean doesImportExistAlready(String requestedChild) {
        return this.imports
                .query()
                .filter((Import node) -> node.hasSameChild(requestedChild))
                .next()
                .isPresent();
    }

    @Override
    public CompileState withLocation(Location location) {
        return new ImmutableCompileState(this.imports, this.output, this.structureNames, this.depth, this.definitions, new Some<Location>(location), this.sources, this.platform, this.dependencies);
    }

    @Override
    public CompileState append(String element) {
        return new ImmutableCompileState(this.imports, this.output + element, this.structureNames, this.depth, this.definitions, this.maybeLocation, this.sources, this.platform, this.dependencies);
    }

    @Override
    public CompileState pushStructureName(String name) {
        return new ImmutableCompileState(this.imports, this.output, this.structureNames.addLast(name), this.depth, this.definitions, this.maybeLocation, this.sources, this.platform, this.dependencies);
    }

    @Override
    public CompileState enterDepth() {
        return new ImmutableCompileState(this.imports, this.output, this.structureNames, this.depth + 1, this.definitions, this.maybeLocation, this.sources, this.platform, this.dependencies);
    }

    @Override
    public CompileState exitDepth() {
        return new ImmutableCompileState(this.imports, this.output, this.structureNames, this.depth - 1, this.definitions, this.maybeLocation, this.sources, this.platform, this.dependencies);
    }

    @Override
    public CompileState defineAll(List<Definition> definitions) {
        return new ImmutableCompileState(this.imports, this.output, this.structureNames, this.depth, this.definitions.addAll(definitions), this.maybeLocation, this.sources, this.platform, this.dependencies);
    }

    @Override
    public Option<Definition> resolve(String name) {
        return this.definitions.queryReversed()
                .filter((Definition definition) -> definition.isNamed(name))
                .next();
    }

    @Override
    public CompileState clearImports() {
        return new ImmutableCompileState(Lists.empty(), this.output, this.structureNames, this.depth, this.definitions, this.maybeLocation, this.sources, this.platform, this.dependencies);
    }

    @Override
    public CompileState clearOutput() {
        return new ImmutableCompileState(this.imports, "", this.structureNames, this.depth, this.definitions, this.maybeLocation, this.sources, this.platform, this.dependencies);
    }

    @Override
    public CompileState addSource(Source source) {
        return new ImmutableCompileState(this.imports, this.output, this.structureNames, this.depth, this.definitions, this.maybeLocation, this.sources.addLast(source), this.platform, this.dependencies);
    }

    @Override
    public Option<Source> findSource(String name) {
        return this.sources.query()
                .filter((Source source) -> Strings.equalsTo(source.createLocation().name(), name))
                .next();
    }

    @Override
    public CompileState addResolvedImportFromCache(String base) {
        if (this.structureNames.query().anyMatch((String inner) -> Strings.equalsTo(inner, base))) {
            return this;
        }

        return this.findSource(base)
                .map((Source source) -> this.addResolvedImport(source.createLocation()))
                .orElse(this);
    }

    @Override
    public CompileState popStructureName() {
        return new ImmutableCompileState(this.imports, this.output, this.structureNames.removeLast().orElse(this.structureNames), this.depth, this.definitions, this.maybeLocation, this.sources, this.platform, this.dependencies);
    }

    @Override
    public CompileState withPlatform(Platform platform) {
        return new ImmutableCompileState(this.imports, this.output, this.structureNames.removeLast().orElse(this.structureNames), this.depth, this.definitions, this.maybeLocation, this.sources, platform, this.dependencies);
    }

    @Override
    public boolean hasPlatform(Platform platform) {
        return this.platform == platform;
    }

    @Override
    public String findOutput() {
        return this.output;
    }

    @Override
    public Iter<Import> queryImports() {
        return this.imports.query();
    }

    @Override
    public Iter<Dependency> queryDependencies() {
        return this.dependencies.query();
    }
}
