package magma.compile.rule;

import magma.compile.CompileError;
import magma.compile.MapNode;
import magma.compile.context.StringContext;
import magma.result.Err;
import magma.result.Result;

public record PrefixRule(String prefix, Rule childRule) implements Rule {
    @Override
    public Result<String, CompileError> generate(MapNode node) {
        return childRule().generate(node).mapValue(inner -> prefix() + inner);
    }

    @Override
    public Result<MapNode, CompileError> parse(String input) {
        if(input.startsWith(prefix)) {
            return childRule.parse(input.substring(prefix.length()));
        } else {
            return new Err<>(new CompileError("Prefix '" + prefix + "' not present", new StringContext(input)));
        }
    }
}