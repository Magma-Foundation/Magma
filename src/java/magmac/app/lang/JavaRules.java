package magmac.app.lang;

import magmac.api.collect.list.Lists;
import magmac.app.compile.rule.LocatingRule;
import magmac.app.compile.rule.NodeRule;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.PrefixRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.Splitter;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.compile.rule.divide.FoldingDivider;
import magmac.app.compile.rule.split.DividingSplitter;
import magmac.app.lang.node.Arguments;
import magmac.app.lang.node.JavaNamespacedNode;
import magmac.app.lang.node.JavaTypes;
import magmac.app.lang.node.StructureMembers;
import magmac.app.lang.node.Structures;
import magmac.app.lang.node.Whitespace;

public class JavaRules {
    public static Rule createConstructionRule() {
        return new TypeRule("construction", new StripRule(new PrefixRule("new ", new NodeRule("type", JavaTypes.createTypeRule()))));
    }

    public static Rule createInvokableRule(Rule value) {
        Rule childRule = new OrRule(Lists.of(JavaRules.createConstructionRule(), value));
        Rule caller = new NodeRule("caller", new SuffixRule(childRule, "("));
        Rule arguments = Arguments.createArgumentsRule(value);
        Splitter splitter = DividingSplitter.Last(new FoldingDivider(new InvocationFolder()), "");
        return new TypeRule("invokable", new StripRule(new SuffixRule(new LocatingRule(caller, splitter, arguments), ")")));
    }

    public static Rule createRootSegmentRule() {
        Rule classMemberRule = StructureMembers.createClassMemberRule();
        return new OrRule(Lists.of(
                Whitespace.createWhitespaceRule(),
                JavaNamespacedNode.createNamespacedRule("package"),
                JavaNamespacedNode.createNamespacedRule("import"),
                Structures.createStructureRule("record", classMemberRule),
                Structures.createStructureRule("interface", classMemberRule),
                Structures.createStructureRule("class", classMemberRule),
                Structures.createStructureRule("enum", classMemberRule)
        ));
    }
}
