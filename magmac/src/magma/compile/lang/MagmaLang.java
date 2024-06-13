package magma.compile.lang;

import magma.compile.rule.LazyRule;
import magma.compile.rule.MembersRule;
import magma.compile.rule.OrRule;
import magma.compile.rule.Rule;
import magma.compile.rule.TypeRule;
import magma.compile.rule.text.LeftRule;
import magma.compile.rule.text.StripRule;
import magma.compile.rule.text.extract.ExtractStringListRule;
import magma.compile.rule.text.extract.ExtractStringRule;
import magma.compile.rule.text.RightRule;
import magma.compile.rule.result.FirstRule;

import java.util.List;

public class MagmaLang {
    private static Rule createStatementRule() {
        var statements = new LazyRule();
        var orRule = new OrRule(List.of(
                createDeclarationRule(),
                createFunctionRule(statements),
                new TypeRule("any", new ExtractStringRule("content"))
        ));
        statements.setRule(orRule);
        return orRule;
    }

    private static TypeRule createDeclarationRule() {
        var left = new ExtractStringRule("name");
        var value = new ExtractStringRule("value");

        return new TypeRule("declaration", new FirstRule(left, " = ", new RightRule(value, "\n")));
    }

    public static TypeRule createRootRule() {
        return new TypeRule("root", new MembersRule("children", new OrRule(List.of(
                new RightRule(new TypeRule("import", new LeftRule("import ", new ExtractStringRule("value"))), "\n"),
                createStatementRule()
        ))));
    }

    private static TypeRule createFunctionRule(LazyRule statements) {
        var modifiers = new ExtractStringListRule("modifiers", " ");
        var name = new ExtractStringRule("name");
        var content = new MembersRule("children", new StripRule(statements));

        return new TypeRule("function", new FirstRule(modifiers, " ", new FirstRule(name, "() => {\n", new RightRule(new StripRule(content), "}\n"))));
    }
}
