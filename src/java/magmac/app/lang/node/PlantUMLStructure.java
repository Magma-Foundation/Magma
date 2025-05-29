package magmac.app.lang.node;

import magmac.app.compile.rule.PrefixRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StringRule;
import magmac.app.compile.rule.TypeRule;

public record PlantUMLStructure(PlantUMLStructureType type, String name) implements PlantUMLRootSegment {
    public static Rule createStructureRule(String type) {
        return new TypeRule(type, new PrefixRule(type + " ", new StringRule("name")));
    }
}
