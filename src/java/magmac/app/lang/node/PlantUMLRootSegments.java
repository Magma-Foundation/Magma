package magmac.app.lang.node;

import magmac.api.collect.list.Lists;
import magmac.app.compile.rule.ExactRule;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;

public final class PlantUMLRootSegments {
    public static Rule createRootSegmentRule() {
        return new SuffixRule(new OrRule(Lists.of(
                new TypeRule("start", new ExactRule("@startuml\nskinparam linetype ortho")),
                new TypeRule("end", new ExactRule("@enduml")),
                PlantUMLStructure.createStructureRule("class"),
                PlantUMLStructure.createStructureRule("interface"),
                PlantUMLStructure.createStructureRule("enum"),
                PlantUMLInherits.createInheritsRule(),
                PlantUMLDependency.createDependencyRule()
        )), "\n");
    }
}
