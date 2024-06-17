package magma.compile.lang;

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
import magma.compile.rule.text.LeftRule;
import magma.compile.rule.text.RightRule;
import magma.compile.rule.text.StripRule;
import magma.compile.rule.text.extract.ExtractNodeRule;
import magma.compile.rule.text.extract.ExtractStringRule;

import java.util.ArrayList;
import java.util.List;

public class JavaLang {
    public static Rule createRootRule() {
        return Lang.createBlock(createRootMemberRule());
    }

    private static OrRule createRootMemberRule() {
        var namespace = Lang.createNamespaceRule();
        var importRule = Lang.createImportRule(namespace);

        var classRule = new LazyRule();
        var classMember = new LazyRule();
        var statement = new LazyRule();
        var valueRule = createValueRule(classMember, statement);

        var rule = JavaDefinitionHeaderFactory.createDefinitionHeaderRule();
        classRule.setRule(createContentRule("class", createContentMember(classRule, rule, statement, classMember, createValueRule(classMember, statement))));

        return new OrRule(List.of(
                new TypeRule("package", new LeftRule("package ", new RightRule(new ExtractNodeRule("internal", namespace), ";"))),
                importRule,
                classRule,
                createContentRule("interface", createContentMember(classRule, rule, statement, classMember, valueRule)),
                createContentRule("record", createContentMember(classRule, rule, statement, classMember, valueRule)),
                Lang.createBlockCommentRule()
        ));
    }

    private static LazyRule createContentMember(LazyRule classRule, Rule definition, LazyRule statement, LazyRule classMember, LazyRule value) {
        var rules = List.of(
                Lang.createBlockCommentRule(),
                Lang.createCommentRule(),
                Lang.createTryRule(statement),
                Lang.createDeclarationRule(definition, value),
                Lang.createAssignmentRule(value),
                Lang.createIfRule("if", value, statement),
                Lang.createIfRule("while", value, statement),
                new TypeRule("invocation", new RightRule(Lang.createInvocationRule(value), ";")),
                Lang.createCatchRule(definition, statement),
                Lang.createReturnRule(value),
                Lang.createForRule(definition, value, statement, ":"),
                Lang.createElseRule(statement),
                Lang.createEmptyStatementRule(),
                Lang.createThrowRule(value),
                Lang.createPostIncrementRule(value),
                Lang.createPostDecrementRule(value),
                Lang.createKeywordRule("break"),
                Lang.createKeywordRule("continue")
        );

        var copy = new ArrayList<>(rules);
        copy.add(new TypeRule("constructor", new RightRule(createConstructorRule(value, classMember), ";")));

        statement.setRule(new OrRule(copy));

        var content = new StripRule(new RightRule(new ExtractNodeRule("child", Lang.createBlock(statement)), "}"));
        var withoutThrows = new StripRule(new RightRule(Lang.createParamsRule(definition), ")"));
        var withThrows = new LastRule(withoutThrows, "throws ", new ExtractNodeRule("thrown", new StripRule(Lang.createTypeRule())));
        var maybeThrows = new OrRule(List.of(withThrows, withoutThrows));

        var withValue = new FirstRule(maybeThrows, "{", content);
        var withoutValue = new RightRule(maybeThrows, ";");
        var maybeValue = new OrRule(List.of(withValue, withoutValue));

        var definitionNode = new ExtractNodeRule("definition", new TypeRule("definition", definition));
        var methodRule = new TypeRule("method", new FirstRule(definitionNode, "(", maybeValue));

        classMember.setRule(new OrRule(List.of(
                methodRule,
                Lang.createDeclarationRule(definition, value),
                Lang.createDefinitionRule(definition),
                Lang.createEmptyStatementRule(),
                Lang.createBlockCommentRule(),
                classRule
        )));
        return classMember;
    }

    private static TypeRule createContentRule(String keyword, LazyRule classMember) {
        var modifiers = Lang.createModifiersRule();
        var block = new ExtractNodeRule("child", Lang.createBlock(classMember));
        var name = createContentMemberRule();
        var withoutModifiers = new FirstRule(new StripRule(name), "{", new RightRule(block, "}"));
        return new TypeRule(keyword, new FirstRule(modifiers, keyword + " ", withoutModifiers));
    }

    private static OrRule createContentMemberRule() {
        var name = new TypeRule("symbol", new StripRule(new ExtractStringRule("value")));
        var prototype = new OrRule(List.of(
                new TypeRule("generic", new FirstRule(new StripRule(new ExtractStringRule("value")), "<", new RightRule(new ExtractStringRule("child"), ">"))),
                name
        ));

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
                createLambdaRule(value, statement),
                createConstructorRule(value, classMember),
                Lang.createTernaryRule(value),
                Lang.createInvocationRule(value),
                Lang.createAccessRule(value),
                Lang.createSymbolRule(),
                Lang.createNumberRule(),
                Lang.createOperatorRule("and", "&&", value),
                Lang.createOperatorRule("equals", "==", value),
                Lang.createOperatorRule("not-equals", "!=", value),
                Lang.createOperatorRule("add", "+", value),
                Lang.createOperatorRule("subtract", "-", value),
                Lang.createOperatorRule("greater-than-or-equals", ">=", value),
                Lang.createOperatorRule("greater-than", ">", value),
                Lang.createOperatorRule("or", "||", value),
                Lang.createOperatorRule("less-than", "<", value),
                Lang.createNotRule(value),
                new TypeRule("method-reference", new LastRule(new ExtractNodeRule("parent", new StripRule(value)), "::", new ExtractStringRule("child")))
        )));
        return value;
    }

    private static Rule createLambdaRule(Rule value, Rule statement) {
        var child = new SymbolRule(new ExtractStringRule("param-name"));
        var left = new StripRule(new OrRule(List.of(new LeftRule("()", new EmptyRule()), child)));
        var maybeValue = new OrRule(List.of(
                new StripRule(new LeftRule("{", new RightRule(Lang.createBlock(statement), "}"))),
                value
        ));

        var right = new StripRule(new ExtractNodeRule("child", maybeValue));
        return new TypeRule("lambda", new FirstRule(left, "->", right));
    }

    private static TypeRule createConstructorRule(LazyRule value, LazyRule classMember) {
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
}
