package magma.compile.lang;

import magma.compile.rule.LazyRule;
import magma.compile.rule.OrRule;
import magma.compile.rule.Rule;
import magma.compile.rule.TypeRule;
import magma.compile.rule.text.extract.ExtractNodeRule;

import java.util.List;

public class MagmaLang {
    public static Rule createRootRule() {
        var statement = new LazyRule();
        statement.setRule(new OrRule(List.of(
                new TypeRule("function", new ExtractNodeRule("child", Lang.createBlock(statement))),
                Lang.createTryRule(statement)
        )));

        return Lang.createBlock(new OrRule(List.of(
                Lang.createImportRule(Lang.createNamespaceRule()),
                statement)));
    }
}
