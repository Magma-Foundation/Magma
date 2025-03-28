package magma.app.compile;

import magma.api.result.Ok;
import magma.api.result.Result;
import magma.api.result.Tuple;
import magma.app.compile.rule.Rule;

class ClassRule implements Rule {
    @Override
    public Result<Tuple<String, String>, CompileError> generate(MapNode node) {
        return new Ok<>(new Tuple<>(node.find(Compiler.HEADER).orElse(""), node.find(Compiler.TARGET).orElse("")));
    }

    @Override
    public Result<MapNode, CompileError> transform(ParseState state, MapNode input) {
        return Compiler.compileClass(state, input.find(INPUT).orElse("")).mapValue(tuple -> {
            return new MapNode().withString(Compiler.HEADER, tuple.left()).withString(Compiler.TARGET, tuple.right());
        });
    }

    @Override
    public Result<MapNode, CompileError> parse(String input) {
        return new Ok<>(new MapNode().withString(INPUT, input));
    }

}
