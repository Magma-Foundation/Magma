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
import magmac.app.compile.rule.fold.DelimitedFolder;

public final class TypescriptLang {
    public static Rule createRule() {
        return new TypeRule("root", DivideRule.Statements("children", new OrRule(Lists.of(
                CommonLang.createWhitespaceRule(),
                TypescriptLang.createImportRule(),
                TypescriptLang.createClassRule("class"),
                TypescriptLang.createClassRule("interface")
        ))));
    }

    private static TypeRule createImportRule() {
        Rule segments = new SuffixRule(new DivideRule("segments", new DelimitedFolder('/'), new StringRule("value")), "\";\n");
        Rule first = LocatingRule.First(new StringRule("child"), " } from \"", segments);
        return new TypeRule("import", new PrefixRule("import { ", first));
    }

    private static Rule createClassRule(String type) {
        Rule children = DivideRule.Statements("children", TypescriptLang.createStructureMemberRule());
        Rule name = LocatingRule.First(new StringRule("name"), " {", new SuffixRule(children, "\n}\n"));
        return new TypeRule(type, new PrefixRule("export " + type + " ", name));
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
