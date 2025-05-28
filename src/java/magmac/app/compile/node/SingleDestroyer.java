package magmac.app.compile.node;

import magmac.api.Tuple2;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileResults;

import java.util.function.Function;

public class SingleDestroyer<T> {
    private final CompileResult<Tuple2<Node, T>> result;

    public SingleDestroyer(CompileResult<Tuple2<Node, T>> result) {
        this.result = result;
    }

    public <R> CompileResult<R> complete(Function<T, R> mapper) {
        return this.result.flatMapValue((Tuple2<Node, T> tuple) -> {
            if (!tuple.left().isEmpty()) {
                return CompileResults.NodeErr("Fields still present", tuple.left());
            }

            return CompileResults.Ok(mapper.apply(tuple.right()));
        });
    }
}
