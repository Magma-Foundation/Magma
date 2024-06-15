package magma.compile.lang;

import magma.compile.rule.OrRule;
import magma.compile.rule.Rule;
import magma.compile.rule.TypeRule;
import magma.compile.rule.split.LastRule;
import magma.compile.rule.text.extract.ExtractStringRule;
import magma.compile.rule.text.extract.SimpleExtractStringListRule;

import java.util.List;

public class MagmaLang {
    public static Rule createRootRule() {
        return Lang.createBlock(new OrRule(List.of(
                Lang.createImportRule(Lang.createNamespaceRule()),
                new TypeRule("function", new LastRule(new SimpleExtractStringListRule("modifiers", " "), " ", new ExtractStringRule("name")))
        )));
    }
}
