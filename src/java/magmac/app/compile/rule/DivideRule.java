package magmac.app.compile.rule;

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileResultCollector;
import magmac.app.compile.error.error.CompileErrors;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.compile.node.NodeList;
import magmac.app.compile.node.NodeListCollector;
import magmac.app.compile.rule.divide.FoldingDivider;
import magmac.app.compile.rule.fold.Folder;

public record DivideRule(String key, Folder folder, Rule childRule) implements Rule {
    @Override
    public CompileResult<Node> lex(String input) {
        return new FoldingDivider(this.folder).divide(input)
                .map((String segment) -> this.childRule.lex(segment))
                .collect(new CompileResultCollector<>(new NodeListCollector()))
                .mapValue((NodeList children) -> new MapNode().withNodeList(this.key, children));
    }

    @Override
    public CompileResult<String> generate(Node node) {
        return node.findNodeList(this.key)
                .map((NodeList list) -> list.join(this.folder.createDelimiter(), (Node child) -> this.childRule.generate(child)))
                .orElseGet(() -> CompileErrors.createNodeError("Node list '" + this.key + "' not present", node));
    }
}