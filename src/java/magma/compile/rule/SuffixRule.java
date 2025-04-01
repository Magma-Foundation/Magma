package magma.compile.rule;

import magma.compile.CompileError;
import magma.compile.MapNode;
import magma.compile.context.StringContext;
import magma.result.Err;
import magma.result.Result;

public record SuffixRule(Rule childRule, String suffix) implements Rule {
    @Override
    public Result<String, CompileError> generate(MapNode mapNode) {
        return childRule().generate(mapNode).mapValue(inner -> inner + suffix());
    }

    @Override
    public Result<MapNode, CompileError> parse(String input) {
        if (input.endsWith(suffix)) {
            String substring = input.substring(0, input.length() - suffix.length());
            return childRule.parse(substring);
        } else {
            return new Err<>(new CompileError("Suffix '" + suffix + "' not present", new StringContext(input)));
        }
    }
}