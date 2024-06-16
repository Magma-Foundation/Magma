package magma.compile.lang;

import magma.compile.rule.EmptyRule;
import magma.compile.rule.LazyRule;
import magma.compile.rule.OrRule;
import magma.compile.rule.Rule;
import magma.compile.rule.TypeRule;
import magma.compile.rule.split.FirstRule;
import magma.compile.rule.split.LastRule;
import magma.compile.rule.text.RightRule;
import magma.compile.rule.text.extract.ExtractNodeRule;
import magma.compile.rule.text.extract.ExtractStringRule;

import java.util.List;

public class MagmaLang {

    public static Rule createRootRule() {
        var statement = new LazyRule();
        var value = new LazyRule();

        var definition = createDefinitionRule();
        value.setRule(new OrRule(List.of(
                createFunctionRule(statement),
                Lang.createCharRule(),
                Lang.createStringRule(),
                Lang.createInvocationRule(value),
                Lang.createAccessRule(value),
                Lang.createSymbolRule(),
                Lang.createTernaryRule(value),
                Lang.createNumberRule(),
                Lang.createOperatorRule("equals", "==", value),
                Lang.createOperatorRule("add", "+", value),
                Lang.createOperatorRule("greater-than", ">", value),
                Lang.createNotRule(value)
        )));

        statement.setRule(new OrRule(List.of(
                Lang.createCommentRule(),
                Lang.createTryRule(statement),
                Lang.createCatchRule(definition, statement),
                Lang.createIfRule("if", value, statement),
                Lang.createIfRule("while", value, statement),
                Lang.createElseRule(statement),
                Lang.createReturnRule(value),
                Lang.createAssignmentRule(value),
                Lang.createForRule(definition, value, statement, " in "),
                createFunctionRule(statement),
                Lang.createDeclarationRule(definition, value),
                new TypeRule("invocation", new RightRule(Lang.createInvocationRule(value), ";")),
                Lang.createEmptyStatementRule(),
                new TypeRule("trait", new EmptyRule()),
                Lang.createThrowRule(value)
        )));

        return Lang.createBlock(new OrRule(List.of(
                Lang.createImportRule(Lang.createNamespaceRule()),
                statement)));
    }

    private static Rule createDefinitionRule() {
        var modifiers = Lang.createModifiersRule();
        var withoutModifiers = new OrRule(List.of(new ExtractStringRule("name"), new EmptyRule()));
        var withModifiers = new LastRule(modifiers, " ", withoutModifiers);

        var maybeModifiers = new OrRule(List.of(
                withModifiers,
                withoutModifiers
        ));

        var definition = new LazyRule();
        var params = new FirstRule(maybeModifiers, "(", new RightRule(Lang.createParamsRule(definition), ")"));
        var maybeParams = new OrRule(List.of(params, maybeModifiers));

        var type = Lang.createTypeRule();
        var withType = new LastRule(maybeParams, " : ", new ExtractNodeRule("type", type));

        var maybeType = new OrRule(List.of(withType, maybeParams));
        definition.setRule(maybeType);
        return definition;
    }

    private static TypeRule createFunctionRule(Rule statement) {
        var child = new ExtractNodeRule("child", new OrRule(List.of(Lang.createBlock(statement), statement)));
        var definition = new ExtractNodeRule("definition", new TypeRule("definition", createDefinitionRule()));
        return new TypeRule("function", new FirstRule(definition, " => {", new RightRule(child, "}")));
    }
}
