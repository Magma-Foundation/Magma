package magmac.app.compile.rule;

import magmac.api.Option;
import magmac.api.iter.collect.Joiner;
import magmac.api.iter.collect.ListCollector;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileResultCollector;
import magmac.app.compile.error.error.CompileErrors;
import magmac.app.compile.node.InlineNodeList;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.compile.node.NodeList;
import magmac.app.compile.rule.divide.FoldingDivider;
import magmac.app.compile.rule.fold.Folder;
import magmac.app.compile.rule.fold.StatementFolder;

public record DivideRule(String key, Folder folder, Rule childRule) implements Rule {
    public static DivideRule Statements(String key, Rule childRule) {
        return new DivideRule(key, new StatementFolder(), childRule);
    }

    private CompileResult<Option<String>> join(NodeList list) {
        return list.iter()
                .map(node -> this.childRule.generate(node))
                .collect(new CompileResultCollector<>(new Joiner(this.folder.createDelimiter())));
    }

    @Override
    public CompileResult<Node> lex(String input) {
        return new FoldingDivider(this.folder).divide(input)
                .map(segment -> this.childRule.lex(segment))
                .collect(new CompileResultCollector<>(new ListCollector<>()))
                .mapValue(children -> new MapNode().withNodeList(this.key(), new InlineNodeList(children)));
    }

    @Override
    public CompileResult<String> generate(Node node) {
        return node.findNodeList(this.key)
                .map(list -> this.join(list))
                .orElseGet(() -> CompileErrors.createNodeError("Node list '" + this.key + "' not present", node))
                .mapValue(value -> value.orElse(""));
    }
}