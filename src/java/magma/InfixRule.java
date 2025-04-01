package magma;

import magma.result.Err;
import magma.result.Result;

public record InfixRule(Rule leftRule, String infix, Rule rightRule) implements Rule {
    @Override
    public Result<String, CompileError> generate(MapNode node) {
        return leftRule.generate(node)
                .and(() -> rightRule.generate(node))
                .mapValue(tuple -> tuple.left() + infix + tuple.right());
    }

    @Override
    public Result<MapNode, CompileError> parse(String input) {
        int contentStart = input.indexOf(infix());
        if (contentStart < 0)
            return new Err<>(new CompileError("Infix '" + infix() + "' not present", new StringContext(input)));

        String beforeContent = input.substring(0, contentStart);
        String substring = input.substring(contentStart + infix().length());

        return leftRule.parse(beforeContent)
                .and(() -> rightRule.parse(substring))
                .mapValue(tuple -> tuple.left().merge(tuple.right()));
    }
}