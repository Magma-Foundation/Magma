package magmac.app.lang;

import magmac.app.compile.rule.DivideRule;
import magmac.app.compile.rule.ExactRule;
import magmac.app.compile.rule.LocatingRule;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.PrefixRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StringRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;

import java.util.List;

public final class TypescriptRoots {
    public static Rule createRule() {
        return new TypeRule("root", DivideRule.Statements("children", new OrRule(List.of(
                CommonLang.createWhitespaceRule(),
                new TypeRule("import", new ExactRule("import { ? } from ?;\n")),
                TypescriptRoots.createClassRule(),
                new TypeRule("interface", new ExactRule("export interface ? {\n}\n")),
                new TypeRule("enum", new ExactRule("export enum ? {\n}\n"))
        ))));
    }

    private static TypeRule createClassRule() {
        DivideRule children = DivideRule.Statements("children", TypescriptRoots.createStructureMemberRule());
        Rule name = LocatingRule.First(new StringRule("name"), " {", new SuffixRule(children, "\n}\n"));
        return new TypeRule("class", new PrefixRule("export class ", name));
    }

    private static Rule createStructureMemberRule() {
        return new OrRule(List.of(
                CommonLang.createWhitespaceRule(),
                new TypeRule("method", new ExactRule("\n\ttemp(){\n\t}")),
                new TypeRule("definition-statement", new ExactRule("\n\ttemp : ?;")),
                new ExactRule("")
        ));
    }
}
