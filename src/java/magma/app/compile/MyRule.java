package magma.app.compile;

import magma.api.result.Ok;
import magma.api.result.Result;
import magma.api.result.Tuple;
import magma.app.compile.rule.MapOutput;
import magma.app.compile.rule.Output;
import magma.app.compile.rule.Rule;

class MyRule implements Rule {
    @Override
    public Result<Output, CompileError> generate(MapNode node) {
        return new Ok<>(new MapOutput().with("header", node.find("header").orElse("")));
    }

    @Override
    public Result<MapNode, CompileError> parse(ParseState state, String input) {
        return apply(state, input).mapValue(tuple -> {
            return new MapNode().withString("header", tuple.left()).withString("target", tuple.right());
        });
    }

    private Result<Tuple<String, String>, CompileError> apply(ParseState state1, String input1) {
        return Compiler.generateEmpty();
    }
}
