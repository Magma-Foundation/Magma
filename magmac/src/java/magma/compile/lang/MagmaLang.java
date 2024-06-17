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

import java.util.List;

public class MagmaLang {

    public static Rule createRootRule() {
        var statement = new LazyRule();
        var value = new LazyRule();

        var definition = createDefinitionRule();
        value.setRule(new OrRule(List.of(
                createFunctionRule(statement, value),
                Lang.createCharRule(),
                Lang.createStringRule(),
                Lang.createInvocationRule(value),
                Lang.createAccessRule("access", ".", value),
                Lang.createSymbolRule(),
                Lang.createTernaryRule(value),
                Lang.createNumberRule(),
                Lang.createOperatorRule("equals", "==", value),
                Lang.createOperatorRule("not-equals", "!=", value),
                Lang.createOperatorRule("add", "+", value),
                Lang.createOperatorRule("greater-than", ">", value),
                Lang.createOperatorRule("or", "||", value),
                Lang.createOperatorRule("and", "&&", value),
                Lang.createOperatorRule("subtract", "-", value),
                Lang.createOperatorRule("less-than", "<", value),
                Lang.createOperatorRule("greater-than-or-equals", ">=", value),
                Lang.createNotRule(value)
        )));

        statement.setRule(new OrRule(List.of(
                Lang.createBlockCommentRule(),
                Lang.createCommentRule(),
                Lang.createTryRule(statement),
                Lang.createCatchRule(definition, statement),
                Lang.createIfRule("if", value, statement),
                Lang.createIfRule("while", value, statement),
                Lang.createElseRule(statement),
                Lang.createReturnRule(value),
                Lang.createAssignmentRule(value),
                Lang.createForRule(definition, value, statement, " in "),
                createFunctionRule(statement, value),

                Lang.createDefinitionRule(definition),
                Lang.createDeclarationRule(definition, value),

                new TypeRule("invocation", new RightRule(Lang.createInvocationRule(value), ";")),
                Lang.createEmptyStatementRule(),
                createStructRule(definition),
                Lang.createThrowRule(value),
                Lang.createPostIncrementRule(value),
                Lang.createPostDecrementRule(value),
                Lang.createKeywordRule("break"),
                Lang.createKeywordRule("continue")
        )));

        return Lang.createBlock(new OrRule(List.of(
                Lang.createImportRule(Lang.createNamespaceRule()),
                statement)));
    }

    private static TypeRule createStructRule(Rule definition) {
        var children = new ExtractNodeRule("child", Lang.createBlock(new RightRule(definition, ";")));
        var child = new FirstRule(new ExtractNodeRule("name", Lang.createNamePrototypeRule()), " {", new RightRule(children, "}"));
        return new TypeRule("struct", new LeftRule("struct ", child));
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

    private static TypeRule createFunctionRule(Rule statement, Rule value) {
        var block = new LeftRule("{", new RightRule(Lang.createBlock(statement), "}"));
        var asBlock = new ExtractNodeRule("child", new OrRule(List.of(block, statement)));
        var asValue = new ExtractNodeRule("child", value);

        var definition = new ExtractNodeRule("definition", new TypeRule("definition", createDefinitionRule()));
        var content = new OrRule(List.of(asValue, asBlock));

        var withDefinition = new FirstRule(definition, " => ", content);
        var withoutDefinition = new LeftRule("() => ", content);

        return new TypeRule("function", new OrRule(List.of(withDefinition, withoutDefinition)));
    }
}
