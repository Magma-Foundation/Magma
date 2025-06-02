package magmac.app.lang.node;

import magmac.api.collect.list.Lists;
import magmac.app.compile.rule.ContextRule;
import magmac.app.compile.rule.FilterRule;
import magmac.app.compile.rule.LocatingRule;
import magmac.app.compile.rule.NodeListRule;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StringRule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.CommonLang;
import magmac.app.lang.ValueFolder;

public final class Structures {
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
