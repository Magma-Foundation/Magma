package magma.app.compile;

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
        return new ImmutableCompileState(this.context, mapper.apply(this.registry()), this.depth, this.stack);
    }

    @Override
    public CompileState mapContext(Function<Context, Context> mapper) {
        return new ImmutableCompileState(mapper.apply(this.context), this.registry, this.depth, this.stack);
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
    public CompileState mapStack(Function<Stack, Stack> mapper) {
        return new ImmutableCompileState(this.context, this.registry, this.depth, mapper.apply(this.stack));
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
