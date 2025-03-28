package magma.app.compile;

import magma.api.result.Ok;
import magma.api.result.Result;
import magma.api.result.Tuple;
import magma.app.compile.rule.MapOutput;
import magma.app.compile.rule.Output;
import magma.app.compile.rule.Rule;

class InterfaceRule implements Rule {
    private static Result<Tuple<String, String>, CompileError> compileInterface(ParseState state, String input) {
        int interfaceIndex = input.indexOf("interface ");
        if (interfaceIndex < 0) return Compiler.createInfixErr(input, "interface ");

        String right = input.substring(interfaceIndex + "interface ".length());
        int contentStart = right.indexOf("{");
        if (contentStart < 0) return Compiler.createInfixErr(right, "{");

        String beforeContent = right.substring(0, contentStart).strip();
        if (beforeContent.endsWith(">")) {
            return Compiler.generateEmpty();
        }

        String name;
        int extendsKeyword = beforeContent.indexOf(" extends ");
        if (extendsKeyword >= 0) {
            name = beforeContent.substring(0, extendsKeyword).strip();
        } else {
            name = beforeContent;
        }

        final Tuple<String, String> content = new Tuple<>("", "");
        MapNode other = new MapNode()
                .withString(Compiler.HEADER, content.left())
                .withString(Compiler.TARGET, content.right());

        return Compiler.generateStruct(new MapNode()
                .withString(Compiler.NAME, name)
                .merge(other));
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

    private Result<Tuple<String, String>, CompileError> apply(ParseState state, String input) {
        return compileInterface(state, input);
    }
}
