package magmac.app.compile.rule;

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileResultCollector;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.compile.node.NodeList;
import magmac.app.compile.node.NodeListCollector;
import magmac.app.compile.rule.divide.Divider;
import magmac.app.compile.rule.divide.FoldingDivider;
import magmac.app.compile.rule.fold.Folder;
import magmac.app.lang.ValueFolder;

public final class NodeListRule implements Rule {
    private final String key;
    private final Rule childRule;
    private final Divider divider;

    public NodeListRule(String key, Rule childRule, Divider divider) {
        this.key = key;
        this.childRule = childRule;
        this.divider = divider;
    }

    public static Rule Values(String key, Rule childRule) {
        return NodeListRule.createNodeListRule(key, new ValueFolder(), childRule);
    }

    public static Rule createNodeListRule(String key, Folder folder, Rule childRule) {
        return new NodeListRule(key, childRule, new FoldingDivider(folder));
    }

    @Override
    public CompileResult<Node> lex(String input) {
        return this.divider.divide(input)
                .map(this.childRule::lex)
                .collect(new CompileResultCollector<>(new NodeListCollector()))
                .mapValue((NodeList children) -> new MapNode().withNodeList(this.key, children));
    }

    @Override
    public CompileResult<String> generate(Node node) {
        return node.findNodeListOrError(this.key)
                .flatMapValue((NodeList list) -> list.join(this.divider.createDelimiter(), this.childRule::generate));
    }
}