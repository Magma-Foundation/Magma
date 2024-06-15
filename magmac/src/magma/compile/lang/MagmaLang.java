package magma.compile.lang;

import magma.compile.rule.EmptyRule;
import magma.compile.rule.LazyRule;
import magma.compile.rule.OrRule;
import magma.compile.rule.Rule;
import magma.compile.rule.TypeRule;
import magma.compile.rule.split.FirstRule;
import magma.compile.rule.split.LastRule;
import magma.compile.rule.text.LeftRule;
import magma.compile.rule.text.RightRule;
import magma.compile.rule.text.extract.ExtractNodeRule;
import magma.compile.rule.text.extract.ExtractStringRule;
import magma.compile.rule.text.extract.SimpleExtractStringListRule;

import java.util.List;

public class MagmaLang {
    public static Rule createRootRule() {
        var statement = new LazyRule();

        var function = new TypeRule("function", new LastRule(new SimpleExtractStringListRule("modifiers", " "), " ",
                new FirstRule(new ExtractStringRule("name"), "() => {", new RightRule(new ExtractNodeRule("child", Lang.createBlock(statement)), "}"))));

        var value = new OrRule(List.of(
                new TypeRule("string", new ExtractStringRule("value"))
        ));

        var name = new ExtractStringRule("name");
        var child = new LastRule(new SimpleExtractStringListRule("modifiers", " "), " ", name);
        var declaration = new TypeRule("declaration", new FirstRule(new OrRule(List.of(child, name)), " = ", new ExtractNodeRule("value", value)));

        statement.setRule(new OrRule(List.of(
                declaration,
                new TypeRule("try", new LeftRule("try {}", new EmptyRule())),
                new TypeRule("catch", new LeftRule("catch (){}", new EmptyRule())),
                new TypeRule("invocation", new LeftRule("();", new EmptyRule())),
                new TypeRule("if", new LeftRule("if (){}", new EmptyRule())),
                new TypeRule("else", new LeftRule("else {}", new EmptyRule())),
                new TypeRule("for", new LeftRule("for (){}", new EmptyRule())),
                new TypeRule("return", new LeftRule("return ", new EmptyRule())),
                function
        )));


        return Lang.createBlock(new OrRule(List.of(
                Lang.createImportRule(Lang.createNamespaceRule()),
                statement)));
    }
}
