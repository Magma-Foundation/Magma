package magmac.app.lang.node;

import magmac.api.collect.list.List;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.LocatingRule;
import magmac.app.compile.rule.NodeListRule;
import magmac.app.compile.rule.PrefixRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StringRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.compile.rule.divide.DelimitedDivider;
import magmac.app.compile.rule.fold.DelimitedFolder;

public record TypeScriptImport(List<Segment> values, List<Segment> segments) implements TypeScriptRootSegment {
    public static Rule createImportRule() {
        Rule segments = new SuffixRule(NodeListRule.createNodeListRule("segments", new DelimitedFolder('/'), new StringRule("value")), "\";\n");
        Rule leftRule = new NodeListRule("values", new StringRule("value"), new DelimitedDivider(", "));
        Rule first = LocatingRule.First(leftRule, " } from \"", segments);
        return new TypeRule("import", new PrefixRule("import { ", first));
    }

    @Override
    public Node serialize() {
        return new MapNode("import")
                .withNodeListSerialized("values", this.values, Segment::serialize)
                .withNodeListSerialized("segments", this.segments, Segment::serialize);
    }
}
