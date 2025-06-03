package magmac.app.lang;

import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.Rule;

public record OptionNodeListRule(String key, Rule ifPresent, Rule ifEmpty) implements Rule {
    @Override
    public CompileResult<Node> lex(String input) {
        return new OrRule(Lists.of(this.ifPresent, this.ifEmpty)).lex(input);
    }

    @Override
    public CompileResult<String> generate(Node node) {
        if (node.hasNodeList(this.key)) {
            return this.ifPresent.generate(node);
        }
        else {
            return this.ifEmpty.generate(node);
        }
    }
}
