package magma.compile.lang;

import magma.compile.rule.LazyRule;
import magma.compile.rule.OrRule;
import magma.compile.rule.Rule;

import java.util.List;

public class MagmaLang {
    public static Rule createRootRule() {
        var statement = new LazyRule();
        statement.setRule(new OrRule(List.of(
        )));


        return Lang.createBlock(new OrRule(List.of(
                Lang.createImportRule(Lang.createNamespaceRule()),
                statement)));
    }
}
