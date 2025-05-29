package magmac.app.lang.node;

import magmac.app.compile.rule.LocatingRule;
import magmac.app.compile.rule.StringRule;
import magmac.app.compile.rule.TypeRule;

public record PlantUMLInherits(String child, String parent) implements PlantUMLRootSegment {
    static TypeRule createInheritsRule() {
        return new TypeRule("inherits", LocatingRule.First(new StringRule("parent"), " <|-- ", new StringRule("child")));
    }
}
