package magmac.app.lang.node;

import magmac.app.compile.rule.LocatingRule;
import magmac.app.compile.rule.StringRule;
import magmac.app.compile.rule.TypeRule;

public record PlantUMLDependency(String child, String parent) implements PlantUMLRootSegment {
    static TypeRule createDependencyRule() {
        return new TypeRule("dependency", LocatingRule.First(new StringRule("child"), " --> ", new StringRule("parent")));
    }
}