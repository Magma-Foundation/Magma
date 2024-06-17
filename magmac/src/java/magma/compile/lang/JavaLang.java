package magma.compile.lang;

import magma.compile.rule.ContextRule;
import magma.compile.rule.EmptyRule;
import magma.compile.rule.LazyRule;
import magma.compile.rule.OrRule;
import magma.compile.rule.Rule;
import magma.compile.rule.SymbolRule;
import magma.compile.rule.TypeRule;
import magma.compile.rule.split.FirstRule;
import magma.compile.rule.split.LastRule;
import magma.compile.rule.split.ParamSplitter;
import magma.compile.rule.split.SplitMultipleRule;
import magma.compile.rule.split.Splitter;
import magma.compile.rule.text.LeftRule;
import magma.compile.rule.text.RightRule;
import magma.compile.rule.text.StripRule;
import magma.compile.rule.text.extract.ExtractNodeRule;
import magma.compile.rule.text.extract.ExtractStringRule;

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

        var definitionHeader = JavaDefinitionHeaderFactory.createDefinitionHeaderRule();
        var value = createValueRule(member, statement);
        initStatements(definitionHeader, statement, member, value);

        var contents = new LazyRule();
        initContentMember(member, contents, definitionHeader, statement, value);

        contents.setRule(new OrRule(List.of(
                createContentRule("class", member),
                createContentRule("record", member),
                createContentRule("interface", member)
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

                Lang.createIfRule("if", value, statement),
                Lang.createElseRule(statement),

                Lang.createIfRule("while", value, statement),
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

    private static TypeRule createContentRule(String keyword, LazyRule classMember) {
        var modifiers = Lang.createModifiersRule();
        var block = new ExtractNodeRule("child", Lang.createBlock(classMember));
        var name = createContentMemberRule();
        var withoutModifiers = new FirstRule(new StripRule(name), "{", new RightRule(block, "}"));
        return new TypeRule(keyword, new FirstRule(modifiers, keyword + " ", withoutModifiers));
    }

    private static OrRule createContentMemberRule() {
        var prototype = Lang.createNamePrototypeRule();

        var leftRule1 = new ExtractNodeRule("name", prototype);
        return new OrRule(List.of(
                new FirstRule(leftRule1, " implements", new ExtractNodeRule("interface", prototype)),
                leftRule1
        ));
    }

    private static LazyRule createValueRule(LazyRule classMember, Rule statement) {
        var value = new LazyRule();
        value.setRule(new OrRule(List.of(
                Lang.createStringRule(),
                Lang.createCharRule(),
                Lang.createSymbolRule(),
                Lang.createNumberRule(),

                createLambdaRule(value, statement),
                createConstructorRule(value, classMember),
                Lang.createTernaryRule(value),
                Lang.createInvocationRule(value),
                Lang.createAccessRule("access", ".", value),
                Lang.createAccessRule("method-reference", "::", value),

                Lang.createOperatorRule("and", "&&", value),
                Lang.createOperatorRule("equals", "==", value),
                Lang.createOperatorRule("not-equals", "!=", value),
                Lang.createOperatorRule("add", "+", value),
                Lang.createOperatorRule("subtract", "-", value),
                Lang.createOperatorRule("greater-than-or-equals", ">=", value),
                Lang.createOperatorRule("greater-than", ">", value),
                Lang.createOperatorRule("or", "||", value),
                Lang.createOperatorRule("less-than", "<", value),
                Lang.createNotRule(value)
        )));
        return value;
    }

    private static Rule createLambdaRule(Rule value, Rule statement) {
        var child = new SymbolRule(new ExtractStringRule("param-name"));
        var left = new StripRule(new OrRule(List.of(new LeftRule("()", new EmptyRule("param-name")), child)));
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
                new FirstRule(caller, "<", new ExtractStringRule("temp")),
                caller
        ));

        var before = new RightRule(new InvocationStartRule(withGenerics, arguments), ")");
        var child = new OrRule(List.of(
                new FirstRule(new StripRule(before), "{", new RightRule(Lang.createBlock(classMember), "}")),
                before
        ));

        return new TypeRule("constructor", new LeftRule("new ", child));
    }

    private static class SplitThrows implements Splitter {
        @Override
        public List<String> split(String input) {
            return Arrays.asList(input.split(","));
        }
    }
}
