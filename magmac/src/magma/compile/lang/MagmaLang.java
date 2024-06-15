package magma.compile.lang;

import magma.compile.rule.EmptyRule;
import magma.compile.rule.LazyRule;
import magma.compile.rule.OrRule;
import magma.compile.rule.Rule;
import magma.compile.rule.TypeRule;
import magma.compile.rule.text.LeftRule;
import magma.compile.rule.text.RightRule;
import magma.compile.rule.text.extract.ExtractNodeRule;
import magma.compile.rule.text.extract.ExtractStringRule;

import java.util.List;

public class MagmaLang {

    public static Rule createRootRule() {
        var statement = new LazyRule();
        statement.setRule(new OrRule(List.of(
                Lang.createTryRule(statement),
                new TypeRule("function", new ExtractNodeRule("child", Lang.createBlock(statement))),
                new TypeRule("declaration", new RightRule(new ExtractStringRule("name"), ";")),
                Lang.createInvocationRule(new LeftRule("?", new EmptyRule()))
        )));

        return Lang.createBlock(new OrRule(List.of(
                Lang.createImportRule(Lang.createNamespaceRule()),
                statement)));
    }
}
