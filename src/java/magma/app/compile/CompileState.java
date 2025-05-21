package magma.app.compile;

import java.util.function.Function;

public interface CompileState {
    String createIndent();

    CompileState mapRegistry(Function<Registry, Registry> mapper);

    CompileState mapContext(Function<Context, Context> mapper);

    CompileState enterDepth();

    CompileState exitDepth();

    CompileState mapStack(Function<Stack, Stack> mapper);

    Context context();

    Registry registry();

    Stack stack();
}
