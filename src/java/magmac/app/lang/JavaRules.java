package magmac.app.lang;

import magmac.api.collect.list.Lists;
import magmac.app.compile.rule.ContextRule;
import magmac.app.compile.rule.FilterRule;
import magmac.app.compile.rule.LocatingRule;
import magmac.app.compile.rule.NodeListRule;
import magmac.app.compile.rule.NodeRule;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.PrefixRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.Splitter;
import magmac.app.compile.rule.StringRule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.compile.rule.divide.FoldingDivider;
import magmac.app.compile.rule.split.DividingSplitter;
import magmac.app.lang.node.Arguments;
import magmac.app.lang.node.JavaDefinition;
import magmac.app.lang.node.JavaNamespacedNode;
import magmac.app.lang.node.JavaTypes;
import magmac.app.lang.node.Modifier;
import magmac.app.lang.node.Parameters;
import magmac.app.lang.node.StructureMembers;
import magmac.app.lang.node.Whitespace;

public final class JavaRules {
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
                JavaRules.createStructureRule("record", classMemberRule),
                JavaRules.createStructureRule("interface", classMemberRule),
                JavaRules.createStructureRule("class", classMemberRule),
                JavaRules.createStructureRule("enum", classMemberRule)
        ));
    }

    public static Rule createRule() {
        return new TypeRule("root", CommonLang.Statements("children", JavaRules.createRootSegmentRule()));
    }

    public static Rule createStructureRule(String keyword, Rule structureMember) {
        Rule name = new StripRule(FilterRule.Symbol(new StringRule("name")));
        Rule beforeContent = JavaDefinition.attachTypeParams(name);

        Rule withParameters = new OrRule(Lists.of(
                new StripRule(new SuffixRule(LocatingRule.First(beforeContent, "(", Parameters.createParametersRule(JavaDefinition.createRule())), ")")),
                beforeContent
        ));

        Rule type = JavaTypes.createTypeRule();
        Rule extended = NodeListRule.createNodeListRule("extended", new ValueFolder(), type);
        Rule withEnds = new OrRule(Lists.of(
                LocatingRule.First(withParameters, " extends ", extended),
                withParameters
        ));

        Rule implemented = NodeListRule.createNodeListRule("implemented", new ValueFolder(), type);
        Rule withImplements = new OrRule(Lists.of(
                new ContextRule("With implements", LocatingRule.First(withEnds, "implements", implemented)),
                new ContextRule("Without implements", withEnds)
        ));

        Rule withPermits = new OrRule(Lists.of(
                LocatingRule.Last(withImplements, " permits ", NodeListRule.Values("variants", type)),
                withImplements
        ));

        Rule afterKeyword = LocatingRule.First(withPermits, "{", new StripRule(new SuffixRule(CommonLang.Statements("children", structureMember), "}")));
        return new TypeRule(keyword, LocatingRule.First(Modifier.createModifiersRule(), keyword + " ", afterKeyword));
    }
}
