package magma.compile.lang;

import magma.compile.rule.ContextRule;
import magma.compile.rule.EmptyRule;
import magma.compile.rule.LazyRule;
import magma.compile.rule.OptionalRule;
import magma.compile.rule.OrRule;
import magma.compile.rule.Rule;
import magma.compile.rule.TypeRule;
import magma.compile.rule.split.FirstRule;
import magma.compile.rule.split.LastRule;
import magma.compile.rule.text.LeftRule;
import magma.compile.rule.text.RightRule;
import magma.compile.rule.text.StripRule;
import magma.compile.rule.text.extract.ExtractNodeRule;
import magma.compile.rule.text.extract.ExtractStringRule;
import magma.compile.rule.text.extract.SimpleExtractStringListRule;

import java.util.List;

public class MagmaLang {

    public static Rule createRootRule() {
        var statement = new LazyRule();
        var value = new LazyRule();

        var definition = createDefinitionRule();
        value.setRule(new ContextRule("Not a value.", new OrRule(List.of(
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
                Lang.createOperatorRule("less-than-or-equals", "<=", value),
                Lang.createNotRule(value),
                new TypeRule("quantity", new StripRule(new LeftRule("(", new RightRule(new ExtractNodeRule("value", value), ")")))),
                createConstructionRule(statement)
        ))));

        statement.setRule(new ContextRule("Not a statement.", new OrRule(List.of(
                Lang.createKeywordRule("break"),
                Lang.createKeywordRule("continue"),
                Lang.createEmptyStatementRule(),
                createCommentRule(),
                Lang.createTryRule(statement),
                Lang.createCatchRule(definition, statement),
                createStructRule(definition, statement, value),
                Lang.createConditionRule("if", value, statement),
                Lang.createConditionRule("while", value, statement),
                Lang.createElseRule(statement),
                Lang.createReturnRule(value),
                Lang.createForRule(definition, value, statement, " in "),

                Lang.createAssignmentRule(value),
                createFunctionRule(statement, value),

                Lang.createDefinitionRule(definition),
                Lang.createDeclarationRule(definition, value),

                new TypeRule("invocation", new RightRule(Lang.createInvocationRule(value), ";")),
                Lang.createThrowRule(value),
                Lang.createPostIncrementRule(value),
                Lang.createPostDecrementRule(value),
                new TypeRule("implements", new LeftRule("implements ", new RightRule(new ExtractNodeRule("type", Lang.createTypeRule()), ";"))),
                Lang.createStatementRule(value)
        ))));

        return Lang.createBlock(new OrRule(List.of(
                Lang.createImportRule(Lang.createNamespaceRule()),
                statement)));
    }

    private static OrRule createCommentRule() {
        return new OrRule(List.of(
                Lang.createBlockCommentRule(),
                Lang.createCommentRule()));
    }

    private static TypeRule createConstructionRule(Rule statement) {
        var child1 = new ExtractNodeRule("child", Lang.createBlock(statement));
        var child = new LeftRule("{", new RightRule(child1, "}"));
        return new TypeRule("construction", new StripRule(child));
    }

    private static TypeRule createStructRule(Rule definition, Rule statement, Rule value) {
        var modifiers = new SimpleExtractStringListRule("modifiers", " ");

        var definition1 = new TypeRule("definition", definition);

        var structMember = new OrRule(List.of(
                new RightRule(definition1, ";"),
                createFunctionRule(statement, value),
                createCommentRule()
        ));
        var children = new ExtractNodeRule("child", Lang.createBlock(structMember));
        var name = new ExtractStringRule("name");

        var child = new FirstRule(name, " {", new RightRule(children, "}"));

        return new TypeRule("struct", new OptionalRule("modifiers",
                new FirstRule(modifiers, " struct ", child),
                new LeftRule("struct ", child)));
    }

    private static Rule createDefinitionRule() {
        var modifiers = Lang.createModifiersRule();
        var withoutModifiers = new OptionalRule("name",
                new ExtractStringRule("name"),
                new EmptyRule("name"));

        var withTypeParams = new StripRule(new FirstRule(withoutModifiers, "<", new RightRule(Lang.createTypeParamsRule(), ">")));
        var maybeTypeParams = new OptionalRule("type-params", withTypeParams, withoutModifiers);

        var withModifiers = new LastRule(modifiers, " ", maybeTypeParams);
        var maybeModifiers = new OptionalRule("modifiers", withModifiers, maybeTypeParams);

        var definition = new LazyRule();
        var params = new FirstRule(maybeModifiers, "(", new RightRule(Lang.createParamsRule(definition), ")"));
        var maybeParams = new OptionalRule("params", params, maybeModifiers);

        var type = Lang.createTypeRule();
        var withType = new LastRule(maybeParams, " : ", new ExtractNodeRule("type", type));
        var maybeType = new OptionalRule("type", withType, maybeParams);

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
