package magmac.app.lang.java.node;

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

public class Structures {
    public static Rule createStructureRule(String keyword) {
        Rule name = new StripRule(FilterRule.Symbol(new StringRule("name")));
        Rule beforeContent = Definition.attachTypeParams(name);

        Rule withParameters = new OrRule(Lists.of(
                new StripRule(new SuffixRule(LocatingRule.First(beforeContent, "(", Parameters.createParametersRule(Definition.createDefinitionRule())), ")")),
                beforeContent
        ));

        Rule extended = new NodeListRule("extended", new ValueFolder(), Types.createTypeRule());
        Rule withEnds = new OrRule(Lists.of(
                LocatingRule.First(withParameters, " extends ", extended),
                withParameters
        ));

        Rule implemented = new NodeListRule("implemented", new ValueFolder(), Types.createTypeRule());
        Rule withImplements = new OrRule(Lists.of(
                new ContextRule("With implements", LocatingRule.First(withEnds, " implements ", implemented)),
                new ContextRule("Without implements", withEnds)
        ));

        Rule afterKeyword = LocatingRule.First(withImplements, "{", new StripRule(new SuffixRule(CommonLang.Statements("children", StructureMembers.createClassMemberRule()), "}")));
        return new TypeRule(keyword, LocatingRule.First(Modifier.createModifiersRule(), keyword + " ", afterKeyword));
    }
}
