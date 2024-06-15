package magma.compile.lang;

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

        var definition = createDefinitionRule();
        var value = new LazyRule();
        value.setRule(new OrRule(List.of(
                Lang.createStringRule(),
                Lang.createInvocationRule(value),
                Lang.createAccessRule(value),
                Lang.createSymbolRule(),
                Lang.createTernaryRule(value)
        )));

        statement.setRule(new OrRule(List.of(
                Lang.createCommentRule(),
                Lang.createTryRule(statement),
                Lang.createCatchRule(definition, statement),
                Lang.createIfRule(value, statement),
                Lang.createElseRule(statement),
                Lang.createReturnRule(value),
                Lang.createAssignmentRule(value),
                Lang.createForRule(definition, value, statement, " in "),
                createFunctionRule(statement),
                Lang.createDeclarationRule(definition, value),
                Lang.createInvocationRule(value)
        )));

        return Lang.createBlock(new OrRule(List.of(
                Lang.createImportRule(Lang.createNamespaceRule()),
                statement)));
    }

    private static Rule createDefinitionRule() {
        var modifiers = Lang.createModifiersRule();
        var withoutModifiers = new ExtractStringRule("name");
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
        var child = new ExtractNodeRule("child", Lang.createBlock(statement));
        return new TypeRule("function", new FirstRule(createDefinitionRule(), " => {", new RightRule(child, "}")));
    }
}
