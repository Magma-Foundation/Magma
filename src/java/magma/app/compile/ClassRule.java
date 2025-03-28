package magma.app.compile;

import magma.api.result.Ok;
import magma.api.result.Result;
import magma.api.result.Tuple;
import magma.app.compile.divide.DivideRule;
import magma.app.compile.rule.MapOutput;
import magma.app.compile.rule.Output;
import magma.app.compile.rule.Rule;

class ClassRule implements Rule {
    private static Result<Tuple<String, String>, CompileError> compileClass(ParseState state, String input) {
        int classIndex = input.indexOf("class ");
        if (classIndex < 0) return Compiler.createInfixErr(input, "class ");

        String right = input.substring(classIndex + "class ".length());
        int contentStart = right.indexOf("{");
        if (contentStart < 0) return Compiler.createInfixErr(input, "{");

        String name = right.substring(0, contentStart).strip();
        String withEnd = right.substring(contentStart + "{".length()).strip();

        int implementsIndex = name.indexOf(" implements ");
        String name1 = implementsIndex >= 0
                ? name.substring(0, implementsIndex).strip()
                : name;

        if (name1.endsWith(">")) {
            return Compiler.generateEmpty();
        }

        if (!withEnd.endsWith("}")) return Compiler.createSuffixErr(input, "}");

        String inputContent = withEnd.substring(0, withEnd.length() - "}".length());
        Rule rule = new DivideRule(new ClassMemberRule());
        return rule.parse(state, inputContent)
                .flatMapValue(rule::generate)
                .mapValue(Output::toTuple)
                .flatMapValue(tuple -> Compiler.generateStruct(new MapNode()
                        .withString(Compiler.NAME, name1)
                        .withString(Compiler.HEADER, tuple.left())
                        .withString(Compiler.TARGET, tuple.right())));

    }

    @Override
    public Result<Output, CompileError> generate(MapNode node) {
        return new Ok<>(new MapOutput().with("header", node.find(Compiler.HEADER).orElse("")));
    }

    @Override
    public Result<MapNode, CompileError> parse(ParseState state, String input) {
        return compileClass(state, input).mapValue(tuple -> {
            return new MapNode().withString(Compiler.HEADER, tuple.left()).withString(Compiler.TARGET, tuple.right());
        });
    }

}
