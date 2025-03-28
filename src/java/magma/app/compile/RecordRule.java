package magma.app.compile;

import magma.api.result.Ok;
import magma.api.result.Result;
import magma.api.result.Tuple;
import magma.app.compile.divide.DivideRule;
import magma.app.compile.rule.MapOutput;
import magma.app.compile.rule.Output;
import magma.app.compile.rule.Rule;

class RecordRule implements Rule {
    private static Result<Tuple<String, String>, CompileError> compileRecord(ParseState state, String input) {
        int recordIndex = input.indexOf("record ");
        if (recordIndex < 0) return Compiler.createInfixErr(input, "record ");

        String right = input.substring(recordIndex + "record ".length());
        int contentStart = right.indexOf("{");
        if (contentStart < 0) return Compiler.createInfixErr(right, "{");

        String beforeContent = right.substring(0, contentStart).strip();
        String withEnd = right.substring(contentStart + "{".length()).strip();

        int paramStart = beforeContent.indexOf("(");
        if (paramStart < 0) return Compiler.createInfixErr(input, "(");

        String maybeWithTypeParams = beforeContent.substring(0, paramStart).strip();
        int implementsIndex = maybeWithTypeParams.indexOf(" implements ");
        String name = implementsIndex >= 0
                ? maybeWithTypeParams.substring(0, implementsIndex).strip()
                : maybeWithTypeParams;

        if (name.endsWith(">")) return Compiler.generateEmpty();

        if (!withEnd.endsWith("}")) return Compiler.createSuffixErr(input, "}");
        String inputContent = withEnd.substring(0, withEnd.length() - "}".length());
        Rule rule = new DivideRule(new ClassMemberRule());
        return rule.parse(state, inputContent)
                .flatMapValue(rule::generate)
                .mapValue(Output::toTuple).flatMapValue(content -> {
                    return Compiler.generateStruct(new MapNode()
                            .withString(Compiler.NAME, name)
                            .merge(new MapNode()
                                    .withString(Compiler.HEADER, content.left())
                                    .withString(Compiler.TARGET, content.right())));
                });
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
        return compileRecord(state, input);
    }
}
