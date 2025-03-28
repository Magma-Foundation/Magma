package magma.app.compile;

import magma.api.result.Err;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.api.result.Tuple;
import magma.app.compile.rule.MapOutput;
import magma.app.compile.rule.Output;
import magma.app.compile.rule.Rule;

public class ClassMemberRule implements Rule {
    private static Result<Tuple<String, String>, CompileError> compileClassMember(String input) {
        if (input.isBlank()) return Compiler.generateEmpty();
        if (input.endsWith(";")) return new Ok<>(new Tuple<>("\tint value;\n", ""));

        int paramStart = input.indexOf("(");
        if (paramStart >= 0) {
            String definition = input.substring(0, paramStart).strip();
            int nameSeparator = definition.lastIndexOf(" ");
            if (nameSeparator >= 0) {
                String oldName = definition.substring(nameSeparator + " ".length()).strip();
                String newName = oldName.equals("main") ? "__main__" : oldName;
                return new Ok<>(new Tuple<>("", "void " + newName + "(){\n}\n"));
            }
        }

        return new Err<>(new CompileError("Invalid class segment", input));
    }

    @Override
    public Result<Output, CompileError> generate(MapNode node) {
        return new Ok<>(new MapOutput().with("header", node.find(Compiler.HEADER).orElse("")));
    }

    @Override
    public Result<MapNode, CompileError> parse(ParseState state, String input) {
        return apply(state, input).mapValue(tuple -> {
            return new MapNode().withString(Compiler.HEADER, tuple.left()).withString(Compiler.TARGET, tuple.right());
        });
    }

    private Result<Tuple<String, String>, CompileError> apply(ParseState parseState2, String s) {
        return compileClassMember(s);
    }
}
