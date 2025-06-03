package magmac.app.lang.node;

import magmac.api.collect.list.Lists;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.SuffixRule;

final class PlantUMLRootSegments {
    public static Rule createRootSegmentRule() {
        return new SuffixRule(new OrRule(Lists.of(
                PlantUMLHeader.createRule(),
                PlantUMLFooter.createRule(),
                PlantUMLStructure.createStructureRule("class"),
                PlantUMLStructure.createStructureRule("interface"),
                PlantUMLStructure.createStructureRule("enum"),
                PlantUMLInherits.createInheritsRule(),
                PlantUMLDependency.createDependencyRule()
        )), "\n");
    }
}
