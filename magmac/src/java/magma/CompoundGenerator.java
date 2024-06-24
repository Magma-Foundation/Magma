package magma;

import magma.api.Tuple;
import magma.api.collect.stream.Streams;
import magma.api.result.Result;
import magma.compile.Error_;
import magma.compile.annotate.State;
import magma.compile.lang.Generator;
import magma.compile.rule.Node;

import java.util.List;

public record CompoundGenerator(List<Generator> children) implements Generator {
    @Override
    public Result<Tuple<Node, State>, Error_> generate(Node root, State state) {
        var initial = new Tuple<>(root, state);
        return Streams.fromNativeList(children()).foldLeftToResult(initial,
                (tuple, generator) -> generator.generate(tuple.left(), tuple.right()));
    }
}