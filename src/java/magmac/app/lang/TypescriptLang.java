package magmac.app.lang;

import magmac.api.collect.list.Lists;
import magmac.app.compile.rule.DivideRule;
import magmac.app.compile.rule.ExactRule;
import magmac.app.compile.rule.LocatingRule;
import magmac.app.compile.rule.NodeRule;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.PrefixRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StringRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.compile.rule.fold.DelimitedFolder;

import java.util.List;

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
                createMethodRule(),
                new TypeRule("statement", new ExactRule("\n\ttemp : ?;"))
        ));
    }

    private static TypeRule createMethodRule() {
        Rule header = new OrRule(Lists.of(
                TypescriptLang.createDefinitionRule(),
                new TypeRule("constructor", new ExactRule("constructor"))
        ));

        return new TypeRule("method", new SuffixRule(new PrefixRule("\n\t", new NodeRule("header", header)), "{\n\t}"));
    }

    private static Rule createDefinitionRule() {
        return LocatingRule.First(new StringRule("name"), " : ", new NodeRule("type", TypescriptLang.createTypeRule()));
    }

    private static ExactRule createTypeRule() {
        return new ExactRule("?");
    }
}
