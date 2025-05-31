package magmac.app.lang.node;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.LocatingRule;
import magmac.app.compile.rule.NodeRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.Splitter;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.compile.rule.divide.FoldingDivider;
import magmac.app.compile.rule.split.DividingSplitter;
import magmac.app.lang.BlockFolder;
import magmac.app.lang.CommonLang;
import magmac.app.lang.LazyRule;
import magmac.app.lang.Deserializers;

record Block(List<FunctionSegment> segments, BlockHeader header) implements FunctionSegment {
    public static Option<CompileResult<Block>> deserialize(Node node) {
        return Deserializers.deserializeWithType(node, "block").map(deserializer -> deserializer
                .withNodeList("children", FunctionSegment::deserialize)
                .withNode("header", BlockHeader::deserialize)
                .complete(tuple -> new Block(tuple.left(), tuple.right())));
    }

    public static Rule createBlockRule(LazyRule functionSegmentRule, Rule value, Rule definition) {
        Rule header = new NodeRule("header", BlockHeader.createBlockHeaderRule(value, definition));
        return createBlockRule0(header, functionSegmentRule);
    }

    public static TypeRule createBlockRule0(Rule header, Rule functionSegmentRule) {
        Rule children = CommonLang.Statements("children", functionSegmentRule);
        Splitter first = DividingSplitter.First(new FoldingDivider(new BlockFolder()), "");
        Rule childRule = new LocatingRule(new SuffixRule(header, "{"), first, children);
        return new TypeRule("block", new StripRule(new SuffixRule(childRule, "}")));
    }

    @Override
    public Node serialize() {
        return new MapNode();
    }
}
