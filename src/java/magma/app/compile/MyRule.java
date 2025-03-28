package magma.app.compile;

import magma.api.result.Ok;
import magma.api.result.Result;
import magma.api.result.Tuple;
import magma.app.compile.rule.MapOutput;
import magma.app.compile.rule.Rule;

class MyRule implements Rule {
    @Override
    public Result<String, CompileError> generate(Node node) {
        return new Ok<>(new MapOutput().with("header", node.findString("header").orElse("")));
    }

    @Override
    public Result<Node, CompileError> parse(ParseState state, String input) {
        return apply(state, input).mapValue(tuple -> {
            return new MapNode().withString("header", tuple.left()).withString("target", tuple.right());
        });
    }

    private Result<Tuple<String, String>, CompileError> apply(ParseState state1, String input1) {
        return Compiler.generateEmpty();
    }
}
