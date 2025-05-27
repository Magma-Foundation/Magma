package magmac.app.lang;

import magmac.app.compile.rule.DivideRule;
import magmac.app.compile.rule.ExactRule;
import magmac.app.compile.rule.InfixRule;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.PrefixRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StringRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.compile.rule.fold.StatementFolder;

import java.util.List;

public final class PlantUMLRoots {
    public static Rule createRule() {
        return new DivideRule("children", new StatementFolder(), PlantUMLRoots.createRootSegmentRule());
    }

    private static SuffixRule createRootSegmentRule() {
        return new SuffixRule(new OrRule(List.of(
                new TypeRule("start", new ExactRule("@startuml\nskinparam linetype ortho")),
                new TypeRule("end", new ExactRule("@enduml")),
                PlantUMLRoots.createStructureRule("class"),
                PlantUMLRoots.createStructureRule("interface"),
                new TypeRule("inherits", new InfixRule(new StringRule("parent"), " <|-- ", new StringRule("child"))),
                new TypeRule("dependency", new InfixRule(new StringRule("child"), " --> ", new StringRule("parent")))
        )), "\n");
    }

    private static TypeRule createStructureRule(String type) {
        return new TypeRule(type, new PrefixRule(type + " ", new StringRule("name")));
    }

}
