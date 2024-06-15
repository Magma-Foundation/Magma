package magma.compile.lang;

import magma.compile.rule.EmptyRule;
import magma.compile.rule.LazyRule;
import magma.compile.rule.OrRule;
import magma.compile.rule.Rule;
import magma.compile.rule.TypeRule;
import magma.compile.rule.split.FirstRule;
import magma.compile.rule.split.LastRule;
import magma.compile.rule.text.RightRule;
import magma.compile.rule.text.StripRule;
import magma.compile.rule.text.extract.ExtractNodeRule;
import magma.compile.rule.text.extract.ExtractStringRule;
import magma.compile.rule.text.extract.SimpleExtractStringListRule;

import java.util.List;

public class MagmaLang {
    public static Rule createRootRule() {
        var statement = new LazyRule();

        var function = new TypeRule("function", new LastRule(new SimpleExtractStringListRule("modifiers", " "), " ",
                new FirstRule(new ExtractStringRule("name"), "() => {", new RightRule(new ExtractNodeRule("child", Lang.createBlock(statement)), "}"))));

        var name = new ExtractStringRule("name");
        var child = new StripRule(new LastRule(new SimpleExtractStringListRule("modifiers", " "), " ", name));
        var declaration = new TypeRule("declaration", new RightRule(new OrRule(List.of(child, name)), " = "));

        statement.setRule(new OrRule(List.of(
                declaration,
                new TypeRule("try", new EmptyRule()),
                new TypeRule("catch", new EmptyRule()),
                new TypeRule("invocation", new EmptyRule()),
                new TypeRule("if", new EmptyRule()),
                new TypeRule("else", new EmptyRule()),
                new TypeRule("for", new EmptyRule()),
                new TypeRule("return", new EmptyRule()),
                function
        )));


        return Lang.createBlock(new OrRule(List.of(
                Lang.createImportRule(Lang.createNamespaceRule()),
                statement)));
    }
}
