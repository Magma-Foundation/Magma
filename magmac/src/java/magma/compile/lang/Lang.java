package magma.compile.lang;

import magma.compile.CompileError;
import magma.compile.Error_;
import magma.compile.rule.ContextRule;
import magma.compile.rule.EmptyRule;
import magma.compile.rule.LazyRule;
import magma.compile.rule.NumberRule;
import magma.compile.rule.OptionalRule;
import magma.compile.rule.OrRule;
import magma.compile.rule.Rule;
import magma.compile.rule.SymbolRule;
import magma.compile.rule.TypeRule;
import magma.compile.rule.split.FirstRule;
import magma.compile.rule.split.LastRule;
import magma.compile.rule.split.MembersSplitter;
import magma.compile.rule.split.ParamSplitter;
import magma.compile.rule.split.SplitMultipleRule;
import magma.compile.rule.split.SplitOnceRule;
import magma.compile.rule.text.LeftRule;
import magma.compile.rule.text.RightRule;
import magma.compile.rule.text.StripRule;
import magma.compile.rule.text.extract.ExtractNodeRule;
import magma.compile.rule.text.extract.ExtractStringListRule;
import magma.compile.rule.text.extract.ExtractStringRule;
import magma.compile.rule.text.extract.SimpleExtractStringListRule;

import java.util.List;
import java.util.Optional;

public class Lang {
    static Rule createBlock(Rule member) {
        return new StripRule(new TypeRule("block", createMembersRule(member)), "before-children", "after-children");
    }

    static SplitMultipleRule createMembersRule(Rule member) {
        return new SplitMultipleRule(new MembersSplitter(), "", "children", new StripRule(member));
    }

    static TypeRule createImportRule(TypeRule namespace) {
        return new TypeRule("import", new LeftRule("import ", new RightRule(new ExtractNodeRule("external", namespace), ";")));
    }

    static TypeRule createNamespaceRule() {
        return new TypeRule("namespace", new SimpleExtractStringListRule("namespace", "."));
    }

    static TypeRule createCatchRule(Rule definition, Rule statement) {
        var declaration = new StripRule(new LeftRule("(", new RightRule(definition, ")")));
        var value = new ExtractNodeRule("child", createBlock(statement));

        var afterKeyword = new FirstRule(new ExtractNodeRule("condition", new TypeRule("definition", declaration)), "{", new RightRule(value, "}"));
        return new TypeRule("catch", new LeftRule("catch ", afterKeyword));
    }

    static LazyRule createTypeRule() {
        var type = new LazyRule();
        var generic = createGenericTypeRule(type);

        type.setRule(new OrRule(List.of(
                new TypeRule("array", new RightRule(new ExtractNodeRule("child", type), "[]")),
                generic,
                new TypeRule("placeholder", new StripRule(new SymbolRule(new ExtractStringRule("value")))),
                new TypeRule("access", new LastRule(new ExtractNodeRule("parent", type), ".", new ExtractStringRule("member"))),
                createFunctionType(type)
        )));

        return type;
    }

    private static TypeRule createGenericTypeRule(LazyRule type) {
        var children = new SplitMultipleRule(new ParamSplitter(), ", ", "children", type);
        var parent = new StripRule(new ExtractStringRule("parent"));

        return new TypeRule("generic", new StripRule(new FirstRule(parent, "<", new RightRule(children, ">"))));
    }

    private static TypeRule createFunctionType(LazyRule type) {
        var params = new SplitMultipleRule(new ParamSplitter(), ", ", "params", type);
        var wrappedParams = new LeftRule("(", new RightRule(params, ")"));
        var maybeParams = new OptionalRule("params", wrappedParams, new LeftRule("()", new EmptyRule("params")));

        var returns = new ExtractNodeRule("returns", type);
        return new TypeRule("function-type", new FirstRule(maybeParams, " => ", returns));
    }

    static TypeRule createTryRule(Rule statement) {
        return new TypeRule("try", new LeftRule("try ", new StripRule(new LeftRule("{", new RightRule(new ExtractNodeRule("child", createBlock(statement)), "}")))));
    }

    static Rule createModifiersRule() {
        return createModifiersRule(List.of("public", "abstract", "static", "private", "final"));
    }

    static Rule createModifiersRule(final List<String> modifiers) {
        return new ExtractStringListRule("modifiers", " ") {
            @Override
            protected Optional<Error_> qualify(String child) {
                if (modifiers.contains(child)) return Optional.empty();
                else return Optional.of(new CompileError("Invalid modifier.", child));
            }
        };
    }

    static TypeRule createInvocationRule(Rule value) {
        var arguments = new OrRule(List.of(
                new SplitMultipleRule(new ParamSplitter(), ", ", "arguments", new StripRule(value))
        ));

        var caller = new ExtractNodeRule("caller", new StripRule(value));
        return new TypeRule("invocation", new RightRule(new SplitOnceRule(caller, "(", arguments, new InvocationStartSearcher()), ")"));
    }

    static TypeRule createCommentRule() {
        return new TypeRule("comment", new LeftRule("//", new ExtractStringRule("value")));
    }

    static TypeRule createConditionRule(String type, Rule value, Rule statement) {
        var child = new ExtractNodeRule("condition", value);
        var conditionParent = new StripRule(new LeftRule("(", child), "before-condition", "after-condition");

        var valueWithBlock = new LeftRule("{", new RightRule(new ExtractNodeRule("value", createBlock(statement)), "}"));
        var valueWithoutBlock = new ExtractNodeRule("value", statement);

        var valueParent = new StripRule(new OrRule(List.of(
                valueWithBlock,
                valueWithoutBlock
        )));

        return new TypeRule(type, new LeftRule(type, new SplitOnceRule(conditionParent, ")", valueParent, new ConditionEndSearcher())));
    }

    static Rule createReturnRule(Rule value) {
        var child = new ExtractNodeRule("child", new StripRule(value));
        var withValue = new LeftRule(" ", new ContextRule("Invalid value.", child));
        var maybeChild = new OrRule(List.of(withValue, new EmptyRule("child")));
        var after = new OrRule(List.of(new RightRule(maybeChild, ";"), maybeChild));
        return new TypeRule("return", new LeftRule("return", after));
    }

    static Rule createForRule(Rule definition, Rule value, Rule statement, String delimiter) {
        var collection = new StripRule(new ExtractNodeRule("collection", value));
        var condition = new RightRule(new LastRule(new StripRule(definition), delimiter, collection), ")");
        var content = new RightRule(new ExtractNodeRule("child", createBlock(statement)), "}");
        var child = new StripRule(new LeftRule("(", condition));
        var leftRule = new ExtractNodeRule("condition-parent", new TypeRule("condition-parent", child));
        var after = new FirstRule(leftRule, "{", content);
        return new TypeRule("for", new LeftRule("for", after));
    }

    static Rule createElseRule(Rule statement) {
        var child = new RightRule(new ExtractNodeRule("child", createBlock(statement)), "}");
        var child1 = new LeftRule("{", child);
        var anElse = new LeftRule("else", new StripRule(child1));
        var anElse1 = new LeftRule("else ", new ExtractNodeRule("child", statement));
        return new TypeRule("else", new OrRule(List.of(anElse, anElse1)));
    }

    static Rule createAssignmentRule(Rule value) {
        var reference = new SymbolRule(new ExtractStringRule("reference"));
        var assignable = new OrRule(List.of(
                new StripRule(reference),
                new LastRule(reference, ".", new ExtractStringRule("member"))
        ));

        var left = new ExtractNodeRule("assignable", new TypeRule("assignable-parent", assignable));
        var right = new RightRule(new ExtractNodeRule("value", new StripRule(value)), ";");
        return new TypeRule("assignment", new FirstRule(left, "=", right));
    }

    static TypeRule createDeclarationRule(Rule definition, Rule value) {
        var wrappedDefinition = new ExtractNodeRule("definition", new TypeRule("definition", definition));
        var formattedDefinition = new StripRule(wrappedDefinition, "", "after-definition");

        var withoutTerminator = new ExtractNodeRule("value", value);
        var withTerminator = new StripRule(new RightRule(withoutTerminator, ";"), "", "value-terminator-spacing");
        var maybeTerminating = new StripRule(new OrRule(List.of(withTerminator, withoutTerminator)), "after-value-separator", "");

        return new TypeRule("declaration", new FirstRule(formattedDefinition, "=", maybeTerminating));
    }

    static Rule createParamsRule(Rule definition) {
        return new SplitMultipleRule(new ParamSplitter(), ", ", "params", new StripRule(new TypeRule("definition", definition)));
    }

    static TypeRule createStringRule() {
        return new TypeRule("string", new LeftRule("\"", new RightRule(new ExtractStringRule("value"), "\"")));
    }

    static TypeRule createAccessRule(String type, String separator, Rule value) {
        var parent = new ExtractNodeRule("parent", new StripRule(value));
        return new TypeRule(type, new LastRule(parent, separator, new StripRule(new SymbolRule(new ExtractStringRule("child")))));
    }

    static TypeRule createSymbolRule() {
        return new TypeRule("symbol", new SymbolRule(new ExtractStringRule("value")));
    }

    static TypeRule createTernaryRule(LazyRule value) {
        return new TypeRule("ternary", new FirstRule(
                new StripRule(new ExtractNodeRule("condition", value)), "?",
                new FirstRule(
                        new StripRule(new ExtractNodeRule("true", value)), ":",
                        new StripRule(new ExtractNodeRule("false", value)))));
    }

    static TypeRule createOperatorRule(String name, String slice, Rule value) {
        var left = new StripRule(new ExtractNodeRule("leftRule", value));
        var right = new StripRule(new ExtractNodeRule("right", value));
        return new TypeRule(name, new SplitOnceRule(left, slice, right, new OperatorSearcher(slice)));
    }

    static TypeRule createNumberRule() {
        return new TypeRule("number", new NumberRule(new ExtractStringRule("value")));
    }

    static TypeRule createCharRule() {
        return new TypeRule("char", new LeftRule("'", new RightRule(new ExtractStringRule("value"), "'")));
    }

    static TypeRule createEmptyStatementRule() {
        return new TypeRule("empty", new RightRule(new StripRule(new EmptyRule("value")), ";"));
    }

    static Rule createThrowRule(Rule value) {
        Rule rule = new ExtractNodeRule("value", value);
        var after = new RightRule(rule, ";");
        return new TypeRule("throw", new LeftRule("throw ", after));
    }

    static TypeRule createNotRule(LazyRule value) {
        return new TypeRule("not", new LeftRule("!", new ExtractNodeRule("child", value)));
    }

    static TypeRule createDefinitionRule(Rule definition) {
        return new TypeRule("definition", new RightRule(definition, ";"));
    }

    static TypeRule createBlockCommentRule() {
        return new TypeRule("block-comment", new StripRule(new LeftRule("/*", new RightRule(new ExtractStringRule("value"), "*/"))));
    }

    static TypeRule createPostDecrementRule(LazyRule value) {
        return new TypeRule("post-decrement", new RightRule(new ExtractNodeRule("value", value), "--;"));
    }

    static TypeRule createPostIncrementRule(LazyRule value) {
        return new TypeRule("post-increment", new RightRule(new ExtractNodeRule("value", value), "++;"));
    }

    static TypeRule createKeywordRule(String keyword) {
        return new TypeRule(keyword, new LeftRule(keyword, new RightRule(new StripRule(new EmptyRule("value")), ";")));
    }

    static SplitMultipleRule createTypeParamsRule() {
        var typeParam = new LazyRule();
        var symbol = new TypeRule("symbol-type", new StripRule(new SymbolRule(new ExtractStringRule("value"))));
        var extendsRule = new TypeRule("extends", new StripRule(new FirstRule(new ExtractStringRule("name"), " extends ", new ExtractNodeRule("child", typeParam))));

        typeParam.setRule(new OrRule(List.of(
                extendsRule,
                symbol
        )));

        return new SplitMultipleRule(new ParamSplitter(), ", ", "type-params", typeParam);
    }

    public static Rule createStatementRule(LazyRule value) {
        var child = new ExtractNodeRule("child", new StripRule(value));
        return new TypeRule("statement", new RightRule(child, ";"));
    }
}
