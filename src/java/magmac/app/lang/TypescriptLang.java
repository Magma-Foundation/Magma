package magmac.app.lang;

import magmac.api.collect.list.Lists;
import magmac.app.compile.rule.DivideRule;
import magmac.app.compile.rule.ExactRule;
import magmac.app.compile.rule.LocatingRule;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.PrefixRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StringRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;

public final class TypescriptLang {
    public static Rule createRule() {
        return new TypeRule("root", DivideRule.Statements("children", new OrRule(Lists.of(
                CommonLang.createWhitespaceRule(),
                TypescriptLang.createImportRule(),
                TypescriptLang.createClassRule(),
                new TypeRule("interface", new ExactRule("export interface ? {\n}\n")),
                new TypeRule("enum", new ExactRule("export enum ? {\n}\n"))
        ))));
    }

    private static TypeRule createImportRule() {
        return new TypeRule("import", new PrefixRule("import { ", new SuffixRule(new StringRule("child"), " } from ?;\n")));
    }

    private static Rule createClassRule() {
        DivideRule children = DivideRule.Statements("children", TypescriptLang.createStructureMemberRule());
        Rule name = LocatingRule.First(new StringRule("name"), " {", new SuffixRule(children, "\n}\n"));
        return new TypeRule("class", new PrefixRule("export class ", name));
    }

    private static Rule createStructureMemberRule() {
        return new OrRule(Lists.of(
                CommonLang.createWhitespaceRule(),
                new TypeRule("method", new ExactRule("\n\ttemp(){\n\t}")),
                new TypeRule("definition-statement", new ExactRule("\n\ttemp : ?;")),
                new ExactRule("")
        ));
    }
}
