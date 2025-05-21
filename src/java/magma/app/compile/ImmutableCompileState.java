package magma.app.compile;

import magma.api.collect.list.Iterable;
import magma.app.Location;
import magma.app.Platform;
import magma.app.compile.define.Definition;
import magma.app.io.Source;

import java.util.function.Function;

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
    public CompileState mapRegistry(Function<Registry, Registry> mapper) {
        return this.createWithRegistry(mapper.apply(this.registry()));
    }

    private CompileState createWithRegistry(Registry registry) {
        return new ImmutableCompileState(this.context, registry, this.depth, this.stack);
    }

    @Override
    public CompileState withLocation(Location location) {
        return this.createWithContext(this.context.withLocation(location));
    }

    private CompileState createWithContext(Context context) {
        return new ImmutableCompileState(context, this.registry, this.depth, this.stack);
    }

    @Override
    public CompileState append(String element) {
        return this.createWithRegistry(this.registry().append(element));
    }

    @Override
    public CompileState pushStructureName(String name) {
        return this.createWithStack(this.stack().pushStructureName(name));
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
        return this.createWithStack(this.stack().defineAll(definitions));
    }

    @Override
    public CompileState clearImports() {
        return this.createWithRegistry(this.registry().clearImports());
    }

    @Override
    public CompileState clearOutput() {
        return this.createWithRegistry(new Registry(this.registry().imports(), this.registry().dependencies(), ""));
    }

    @Override
    public CompileState addSource(Source source) {
        return this.createWithContext(this.context().addSource(source));
    }

    @Override
    public CompileState popStructureName() {
        return this.createWithStack(this.stack().popStructureName());
    }

    private CompileState createWithStack(Stack stack) {
        return new ImmutableCompileState(this.context, this.registry, this.depth, stack);
    }

    @Override
    public CompileState withPlatform(Platform platform) {
        return new ImmutableCompileState(new Context(platform, this.context().maybeLocation(), this.context().sources()), this.registry, this.depth, this.stack);
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
