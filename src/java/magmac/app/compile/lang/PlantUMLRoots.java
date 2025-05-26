package magmac.app.compile.lang;

import magmac.app.compile.node.Node;
import magmac.app.compile.rule.DivideRule;
import magmac.app.compile.rule.InfixRule;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.PrefixRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StringRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;

import java.util.List;

public class PlantUMLRoots {
    public static Rule createRule() {
        return new DivideRule("children", PlantUMLRoots.createRootSegmentRule());
    }

    private static SuffixRule createRootSegmentRule() {
        return new SuffixRule(new OrRule(List.of(
                new TypeRule("class", new PrefixRule("class ", new StringRule("name"))),
                new TypeRule("dependency", new InfixRule(new StringRule("parent"), " --> ", new StringRule("child")))
        )), "\n");
    }

    public static String generate(Node root) {
        String generated = createRule().generate(root)
                .toOptional()
                .orElse("");

        return "@startuml\nskinparam linetype ortho\n" +
                generated +
                "@enduml\n";
    }
}
