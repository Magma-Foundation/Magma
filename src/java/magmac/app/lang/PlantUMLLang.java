package magmac.app.lang;

import magmac.api.collect.list.Lists;
import magmac.app.compile.rule.NodeListRule;
import magmac.app.compile.rule.ExactRule;
import magmac.app.compile.rule.LocatingRule;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.PrefixRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StringRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.compile.rule.fold.StatementFolder;
import magmac.app.lang.java.node.Whitespace;

public final class PlantUMLLang {
    public static Rule createRule() {
        return new NodeListRule("children", new StatementFolder(), PlantUMLLang.createRootSegmentRule());
    }

    private static SuffixRule createRootSegmentRule() {
        return new SuffixRule(new OrRule(Lists.of(
                Whitespace.createWhitespaceRule(),
                new TypeRule("start", new ExactRule("@startuml\nskinparam linetype ortho")),
                new TypeRule("end", new ExactRule("@enduml")),
                PlantUMLLang.createStructureRule("class"),
                PlantUMLLang.createStructureRule("interface"),
                PlantUMLLang.createStructureRule("enum"),
                new TypeRule("inherits", LocatingRule.First(new StringRule("parent"), " <|-- ", new StringRule("child"))),
                new TypeRule("dependency", LocatingRule.First(new StringRule("child"), " --> ", new StringRule("parent")))
        )), "\n");
    }

    private static Rule createStructureRule(String type) {
        return new TypeRule(type, new PrefixRule(type + " ", new StringRule("name")));
    }

}
