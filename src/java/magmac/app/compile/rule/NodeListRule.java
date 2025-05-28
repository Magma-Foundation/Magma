package magmac.app.compile.rule;

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileResultCollector;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.compile.node.NodeList;
import magmac.app.compile.node.NodeListCollector;
import magmac.app.compile.rule.divide.FoldingDivider;
import magmac.app.compile.rule.fold.Folder;
import magmac.app.lang.ValueFolder;

public record NodeListRule(String key, Folder folder, Rule childRule) implements Rule {
    public static NodeListRule Values(String key, Rule childRule) {
        return new NodeListRule(key, new ValueFolder(), childRule);
    }

    @Override
    public CompileResult<Node> lex(String input) {
        return new FoldingDivider(this.folder).divide(input)
                .map((String segment) -> this.childRule.lex(segment))
                .collect(new CompileResultCollector<>(new NodeListCollector()))
                .mapValue((NodeList children) -> new MapNode().withNodeList(this.key, children));
    }

    @Override
    public CompileResult<String> generate(Node node) {
        return node.findNodeListOrError(this.key)
                .flatMapValue((NodeList list) -> list.join(this.folder.createDelimiter(), (Node child) -> this.childRule.generate(child)));
    }

}