package magma.app.compile;

import java.util.function.Function;

public final class ImmutableCompileState implements CompileState {
    private final Context context;
    private final Registry registry;
    private final int depth;
    private final Stack stack;

    private ImmutableCompileState(
            Context context,
            Registry registry,
            Stack stack, int depth
    ) {
        this.context = context;
        this.registry = registry;
        this.depth = depth;
        this.stack = stack;
    }

    public static CompileState createEmpty() {
        return new ImmutableCompileState(
                ImmutableContext.createEmpty(),
                ImmutableRegistry.createEmpty(),
                ImmutableStack.createEmpty(),
                0
        );
    }

    @Override
    public String createIndent() {
        return "\n" + "\t".repeat(this.depth);
    }

    @Override
    public CompileState mapRegistry(Function<Registry, Registry> mapper) {
        return new ImmutableCompileState(this.context, mapper.apply(this.registry()), this.stack, this.depth);
    }

    @Override
    public CompileState mapContext(Function<Context, Context> mapper) {
        return new ImmutableCompileState(mapper.apply(this.context), this.registry, this.stack, this.depth);
    }

    @Override
    public CompileState enterDepth() {
        return new ImmutableCompileState(this.context, this.registry, this.stack, this.depth + 1);
    }

    @Override
    public CompileState exitDepth() {
        return new ImmutableCompileState(this.context, this.registry, this.stack, this.depth - 1);
    }

    @Override
    public CompileState mapStack(Function<Stack, Stack> mapper) {
        return new ImmutableCompileState(this.context, this.registry, mapper.apply(this.stack), this.depth);
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
