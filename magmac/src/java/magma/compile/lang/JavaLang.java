package magma.compile.lang;

import magma.compile.rule.ContextRule;
import magma.compile.rule.LazyRule;
import magma.compile.rule.OptionalRule;
import magma.compile.rule.OrRule;
import magma.compile.rule.Rule;
import magma.compile.rule.SymbolRule;
import magma.compile.rule.TypeRule;
import magma.compile.rule.split.FirstRule;
import magma.compile.rule.split.LastRule;
import magma.compile.rule.split.ParamSplitter;
import magma.compile.rule.split.SplitMultipleRule;
import magma.compile.rule.split.SplitOnceRule;
import magma.compile.rule.split.Splitter;
import magma.compile.rule.text.LeftRule;
import magma.compile.rule.text.RightRule;
import magma.compile.rule.text.StripRule;
import magma.compile.rule.text.extract.ExtractNodeRule;
import magma.compile.rule.text.extract.ExtractStringRule;
import magma.compile.rule.text.extract.SimpleExtractStringListRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JavaLang {
    public static Rule createRootRule() {
        return Lang.createBlock(createRootMemberRule());
    }

    private static OrRule createRootMemberRule() {
        var namespace = Lang.createNamespaceRule();
        var importRule = Lang.createImportRule(namespace);

        var member = new LazyRule();
        var statement = new LazyRule();

        var definition = JavaDefinitionHeaderFactory.createDefinitionHeaderRule();
        var value = createValueRule(member, statement);
        initStatements(definition, statement, member, value);

        var contents = new LazyRule();
        initContentMember(member, contents, definition, statement, value);

        contents.setRule(new OrRule(List.of(
                createContentRule("class", member, Lang.createTypeRule(), definition),
                createContentRule("record", member, Lang.createTypeRule(), definition),
                createContentRule("interface", member, Lang.createTypeRule(), definition)
        )));

        return new OrRule(List.of(
                new TypeRule("package", new LeftRule("package ", new RightRule(new ExtractNodeRule("internal", namespace), ";"))),
                importRule,
                contents,
                Lang.createBlockCommentRule()
        ));
    }

    private static void initContentMember(
            LazyRule contentMember,
            Rule contents,
            Rule definition,
            Rule statement,
            Rule value) {
        var content = new StripRule(new RightRule(new ExtractNodeRule("child", Lang.createBlock(statement)), "}"));
        var withoutThrows = new ContextRule("No throws statement present.", new StripRule(new RightRule(Lang.createParamsRule(definition), ")")));

        var thrownValues = new SplitMultipleRule(new SplitThrows(), ", ", "thrown", new StripRule(Lang.createTypeRule()));
        var withThrows = new ContextRule("Throws statement present.", new LastRule(withoutThrows, "throws ", thrownValues));
        var maybeThrows = new OrRule(List.of(withThrows, withoutThrows));

        var withValue = new ContextRule("Value present.", new FirstRule(maybeThrows, "{", content));
        var withoutValue = new ContextRule("No value present.", new RightRule(maybeThrows, ";"));
        var maybeValue = new OrRule(List.of(withValue, withoutValue));

        var definitionNode = new ExtractNodeRule("definition", new TypeRule("definition", definition));
        var methodRule = new TypeRule("method", new FirstRule(definitionNode, "(", maybeValue));

        contentMember.setRule(new OrRule(List.of(
                Lang.createEmptyStatementRule(),
                Lang.createBlockCommentRule(),
                methodRule,
                Lang.createDeclarationRule(definition, value),
                Lang.createDefinitionRule(definition),
                contents
        )));
    }

    private static void initStatements(Rule definition, LazyRule statement, LazyRule classMember, LazyRule value) {
        var rules = List.of(
                Lang.createBlockCommentRule(),
                Lang.createCommentRule(),

                Lang.createKeywordRule("break"),
                Lang.createKeywordRule("continue"),
                Lang.createReturnRule(value),

                Lang.createConditionRule("if", value, statement),
                Lang.createElseRule(statement),

                Lang.createConditionRule("while", value, statement),
                Lang.createForRule(definition, value, statement, ":"),

                Lang.createTryRule(statement),
                Lang.createCatchRule(definition, statement),
                Lang.createThrowRule(value),

                Lang.createDeclarationRule(definition, value),
                Lang.createAssignmentRule(value),
                new TypeRule("invocation", new RightRule(Lang.createInvocationRule(value), ";")),
                Lang.createEmptyStatementRule(),
                Lang.createPostIncrementRule(value),
                Lang.createPostDecrementRule(value)
        );

        var copy = new ArrayList<>(rules);
        copy.add(new TypeRule("constructor", new RightRule(createConstructorRule(value, classMember), ";")));
        statement.setRule(new OrRule(copy));
    }

    private static TypeRule createContentRule(String keyword, LazyRule classMember, LazyRule type, Rule definition) {
        var modifiers = Lang.createModifiersRule();
        var block = new ExtractNodeRule("child", Lang.createBlock(classMember));

        var name = new StripRule(new SymbolRule(new ExtractStringRule("name")));

        var typeParams = Lang.createTypeParamsRule();
        var withTypeParams = new StripRule(new FirstRule(name, "<", new RightRule(typeParams, ">")));
        var maybeTypeParams = new OptionalRule("type-params", withTypeParams, name);

        var params = Lang.createParamsRule(definition);
        var withParams = new FirstRule(maybeTypeParams, "(", new StripRule(new RightRule(params, ")")));
        var maybeParams = new OptionalRule("params", withParams, maybeTypeParams);

        var withExtends = new FirstRule(maybeParams, " extends ", new StripRule(new ExtractStringRule("superclass")));
        var maybeExtends = new OptionalRule("extends", withExtends, maybeParams);

        var withImplements = new FirstRule(maybeExtends, " implements ", new ExtractNodeRule("interface", type));
        var maybeImplements = new OptionalRule("implements", withImplements, maybeExtends);

        var withoutModifiers = new FirstRule(maybeImplements, "{", new RightRule(block, "}"));
        return new TypeRule(keyword, new FirstRule(modifiers, keyword + " ", withoutModifiers));
    }

    private static LazyRule createValueRule(LazyRule classMember, Rule statement) {
        var value = new LazyRule();
        var parent = new ExtractNodeRule("parent", new StripRule(value));
        value.setRule(new OrRule(List.of(
                Lang.createStringRule(),
                Lang.createCharRule(),
                Lang.createSymbolRule(),
                Lang.createNumberRule(),

                createLambdaRule(value, statement),
                createConstructorRule(value, classMember),
                Lang.createTernaryRule(value),
                Lang.createInvocationRule(value),
                createAccessRule(parent, Lang.createTypeRule()),
                Lang.createAccessRule("method-reference", "::", value),

                Lang.createOperatorRule("and", "&&", value),
                Lang.createOperatorRule("equals", "==", value),
                Lang.createOperatorRule("not-equals", "!=", value),
                Lang.createOperatorRule("add", "+", value),
                Lang.createOperatorRule("subtract", "-", value),
                Lang.createOperatorRule("greater-than-or-equals", ">=", value),
                Lang.createOperatorRule("less-than-or-equals", "<=", value),
                Lang.createOperatorRule("greater-than", ">", value),
                Lang.createOperatorRule("or", "||", value),
                Lang.createOperatorRule("less-than", "<", value),
                Lang.createNotRule(value)
        )));
        return value;
    }

    private static TypeRule createAccessRule(Rule parent, Rule type) {
        var withoutTypeArguments = new StripRule(new SymbolRule(new ExtractStringRule("child")));
        var withTypeArguments = new StripRule(new LeftRule("<", new LastRule(new ExtractNodeRule("type", type), ">", withoutTypeArguments)));
        var child = new StripRule(new OrRule(List.of(withTypeArguments, withoutTypeArguments)));
        return new TypeRule("access", new LastRule(parent, ".", child));
    }

    private static Rule createLambdaRule(Rule value, Rule statement) {
        var asMultiple = new StripRule(new LeftRule("(", new RightRule(new SimpleExtractStringListRule("params", ","), ")")));
        var asSingle = new StripRule(new SymbolRule(new ExtractStringRule("param")));

        var left = new OrRule(List.of(asMultiple, asSingle));
        var maybeValue = new OrRule(List.of(
                new StripRule(new LeftRule("{", new RightRule(Lang.createBlock(statement), "}"))),
                value
        ));

        var right = new StripRule(new ExtractNodeRule("child", maybeValue));
        return new TypeRule("lambda", new FirstRule(left, "->", right));
    }

    private static Rule createConstructorRule(Rule value, Rule classMember) {
        var arguments = new OrRule(List.of(
                new SplitMultipleRule(new ParamSplitter(), ", ", "arguments", new StripRule(value))
        ));

        var caller = new ExtractNodeRule("caller", value);
        var withGenerics = new OrRule(List.of(
                new FirstRule(caller, "<", new RightRule(new ExtractStringRule("generics"), ">")),
                caller
        ));

        var before = new RightRule(new SplitOnceRule(withGenerics, "(", arguments, new InvocationStartSearcher()), ")");
        var child = new OrRule(List.of(
                new FirstRule(new StripRule(before), "{", new RightRule(Lang.createBlock(classMember), "}")),
                before
        ));

        return new TypeRule("constructor", new StripRule(new LeftRule("new ", child)));
    }

    private static class SplitThrows implements Splitter {
        @Override
        public List<String> split(String input) {
            return Arrays.asList(input.split(","));
        }
    }
}
