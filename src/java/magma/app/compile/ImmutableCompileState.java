package magma.app.compile;

import jvm.api.collect.list.Lists;
import magma.api.collect.list.Iterable;
import magma.api.option.Some;
import magma.app.Location;
import magma.app.Platform;
import magma.app.compile.define.Definition;
import magma.app.io.Source;

public final class ImmutableCompileState implements CompileState {
    private final Context context;
    private final Registry registry;
    private final int depth;
    private final Stack stack;

    public ImmutableCompileState(
            Context context,
            Registry registry,
            int depth,
            Stack stack
    ) {
        this.context = context;
        this.registry = registry;
        this.depth = depth;
        this.stack = stack;
    }

    @Override
    public String createIndent() {
        return "\n" + "\t".repeat(this.depth);
    }

    @Override
    public CompileState addResolvedImport(Location location) {
        if (Platform.PlantUML == this.context().platform()) {
            var name = this.context().maybeLocation().map(Location::name).orElse("");
            var dependency = new Dependency(name, location.name());
            if (!this.registry().dependencies().contains(dependency)) {
                return new ImmutableCompileState(
                        this.context,
                        new Registry(this.registry().imports(), this.registry().dependencies().addLast(dependency), this.registry().output()), this.depth,
                        this.stack);
            }
        }

        var requestedNamespace = location.namespace();
        var requestedChild = location.name();

        var thisNamespace = this.context().maybeLocation()
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

        if (this.registry().doesImportExistAlready(requestedChild)) {
            return this;
        }

        var newNamespace = requestedNamespace.addLast(requestedChild);
        var importString = new Import(newNamespace, requestedChild);
        return new ImmutableCompileState(this.context, new Registry(this.registry().imports().addLast(importString), this.registry().dependencies(), this.registry().output()), this.depth, this.stack);
    }

    @Override
    public CompileState withLocation(Location location) {
        return new ImmutableCompileState(new Context(this.context().platform(), new Some<Location>(location), this.context().sources()), this.registry, this.depth, this.stack);
    }

    @Override
    public CompileState append(String element) {
        return new ImmutableCompileState(this.context, new Registry(this.registry().imports(), this.registry().dependencies(), this.registry().output() + element), this.depth, this.stack);
    }

    @Override
    public CompileState pushStructureName(String name) {
        return new ImmutableCompileState(this.context, this.registry, this.depth, new Stack(this.stack().structureNames().addLast(name), this.stack().definitions()));
    }

    @Override
    public CompileState enterDepth() {
        return new ImmutableCompileState(this.context, this.registry, this.depth + 1, this.stack);
    }

    @Override
    public CompileState exitDepth() {
        return new ImmutableCompileState(this.context, this.registry, this.depth - 1, this.stack);
    }

    @Override
    public CompileState defineAll(Iterable<Definition> definitions) {
        return new ImmutableCompileState(this.context, this.registry, this.depth, new Stack(this.stack().structureNames(), this.stack().definitions().addAll(definitions)));
    }

    @Override
    public CompileState clearImports() {
        return new ImmutableCompileState(this.context, new Registry(Lists.empty(), this.registry().dependencies(), this.registry().output()), this.depth, this.stack);
    }

    @Override
    public CompileState clearOutput() {
        return new ImmutableCompileState(this.context, new Registry(this.registry().imports(), this.registry().dependencies(), ""), this.depth, this.stack);
    }

    @Override
    public CompileState addSource(Source source) {
        return new ImmutableCompileState(new Context(this.context().platform(), this.context().maybeLocation(), this.context().sources().addLast(source)), this.registry, this.depth, this.stack);
    }

    @Override
    public CompileState addResolvedImportFromCache(String base) {
        if (this.stack().hasAnyStructureName(base)) {
            return this;
        }

        return this.context().findSource(base)
                .map((Source source) -> this.addResolvedImport(source.createLocation()))
                .orElse(this);
    }

    @Override
    public CompileState popStructureName() {
        return new ImmutableCompileState(this.context, this.registry, this.depth, new Stack(this.stack().structureNames().removeLast().orElse(this.stack().structureNames()), this.stack().definitions()));
    }

    @Override
    public CompileState withPlatform(Platform platform) {
        return new ImmutableCompileState(new Context(platform, this.context().maybeLocation(), this.context().sources()), this.registry, this.depth, new Stack(this.stack().structureNames().removeLast().orElse(this.stack().structureNames()), this.stack().definitions()));
    }

    @Override
    public Context context() {
        return this.context;
    }

    @Override
    public Registry registry() {
        return this.registry;
    }

    @Override
    public Stack stack() {
        return this.stack;
    }
}
