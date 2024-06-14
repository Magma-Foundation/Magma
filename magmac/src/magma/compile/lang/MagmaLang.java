package magma.compile.lang;

import magma.compile.rule.LazyRule;
import magma.compile.rule.MembersRule;
import magma.compile.rule.OrRule;
import magma.compile.rule.Rule;
import magma.compile.rule.TypeRule;
import magma.compile.rule.result.FirstRule;
import magma.compile.rule.result.LastRule;
import magma.compile.rule.text.LeftRule;
import magma.compile.rule.text.RightRule;
import magma.compile.rule.text.StripRule;
import magma.compile.rule.text.extract.ExtractNodeRule;
import magma.compile.rule.text.extract.ExtractStringListRule;
import magma.compile.rule.text.extract.ExtractStringRule;

import java.util.List;

public class MagmaLang {
    private static Rule createStatementRule() {
        var statements = new LazyRule();
        var orRule = new OrRule(List.of(
                createDeclarationRule(),
                createFunctionRule(statements),
                createObjectRule(statements),
                new TypeRule("any", new ExtractStringRule("content"))
        ));
        statements.setRule(orRule);
        return orRule;
    }

    private static Rule createObjectRule(Rule statements) {
        var name = new LeftRule("object ", new FirstRule(new ExtractStringRule("name"), " {\n", new RightRule(new ExtractNodeRule("content", createBlock(statements)), "}")));
        var child = new LastRule(new ExtractStringListRule("modifiers", " "), " ", name);
        return new TypeRule("object", child);
    }

    private static TypeRule createDeclarationRule() {
        var definition = createDefinitionRule();
        var value = new ExtractStringRule("value");

        return new TypeRule("declaration", new FirstRule(definition, " = ", new RightRule(value, "\n")));
    }

    private static LastRule createDefinitionRule() {
        var withoutParams = new ExtractStringRule("name");
        var withParams = new FirstRule(withoutParams, "(", new RightRule(new ExtractStringRule("params"), ")"));

        var withoutType = new OrRule(List.of(withParams, withoutParams));
        var withType = new LastRule(withoutType, " : ", new ExtractStringRule("type"));
        var anyType = new OrRule(List.of(withType, withoutType));

        return new LastRule(new ExtractStringListRule("modifiers", " "), " ", anyType);
    }

    public static TypeRule createRootRule() {
        return createBlock(new OrRule(List.of(
                new RightRule(new TypeRule("import", new LeftRule("import ", new ExtractStringRule("value"))), "\n"),
                createStatementRule()
        )));
    }

    private static TypeRule createBlock(Rule values) {
        return new TypeRule("block", new MembersRule("children", values));
    }

    private static TypeRule createFunctionRule(LazyRule statements) {
        var definition = createDefinitionRule();
        var content = new ExtractNodeRule("content", new TypeRule("block", new MembersRule("children", new StripRule(statements))));
        return new TypeRule("function", new FirstRule(definition, " => {\n", new RightRule(new StripRule(content), "}\n")));
    }
}
