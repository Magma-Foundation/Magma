package magma.compile.lang;

import magma.compile.rule.EmptyRule;
import magma.compile.rule.LazyRule;
import magma.compile.rule.NumberRule;
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
        var classRule = createClassRule();
        var namespace = Lang.createNamespaceRule();
        var importRule = Lang.createImportRule(namespace);

        return new OrRule(List.of(
                new TypeRule("package", new LeftRule("package ", new RightRule(new ExtractNodeRule("internal", namespace), ";"))),
                importRule,
                classRule
        ));
    }

    private static TypeRule createClassRule() {
        var definition = createDefinitionHeaderRule();

        var value = new LazyRule();
        var constructor = createConstructorRule(value);
        var invocation = Lang.createInvocationRule(value);

        initValues(value, constructor, invocation);

        var declaration = new TypeRule("declaration", new FirstRule(new StripRule(definition), "=", new RightRule(new StripRule(new ExtractNodeRule("value", value)), ";")));

        var statement = new LazyRule();

        var rules = List.<Rule>of(
                new TypeRule("comment", new LeftRule("//", new ExtractStringRule("value"))),
                Lang.createTryRule(statement),
                declaration,
                new TypeRule("assignment", new FirstRule(new StripRule(new SymbolRule(new ExtractStringRule("reference"))), "=", new RightRule(new StripRule(new ExtractNodeRule("value", value)), ";"))),
                new TypeRule("invocation", new RightRule(invocation, ";")),
                Lang.createCatchRule(definition, statement),
                new TypeRule("if", new LeftRule("if", new FirstRule(new StripRule(new LeftRule("(", new RightRule(new ExtractNodeRule("condition", value), ")"))), "{", new RightRule(new ExtractNodeRule("child", Lang.createBlock(statement)), "}")))),
                new TypeRule("return", new LeftRule("return", new RightRule(new StripRule(new OrRule(List.of(new EmptyRule(), new ExtractNodeRule("child", value)))), ";"))),
                new TypeRule("for", new LeftRule("for", new FirstRule(new StripRule(new LeftRule("(", new RightRule(new LastRule(new StripRule(definition), ":", new StripRule(new ExtractNodeRule("collection", value))), ")"))), "{", new RightRule(new ExtractNodeRule("child", Lang.createBlock(statement)), "}")))),
                new TypeRule("else", new LeftRule("else", new StripRule(new LeftRule("{", new RightRule(new ExtractNodeRule("child", Lang.createBlock(statement)), "}")))))
        );

        var copy = new ArrayList<>(rules);
        copy.add(new TypeRule("constructor", new RightRule(constructor, ";")));

        statement.setRule(new OrRule(copy));

        var params = new SplitMultipleRule(new ParamSplitter(), ", ", "params", new StripRule(new TypeRule("definition", definition)));
        var content = new StripRule(new LeftRule("{", new RightRule(new ExtractNodeRule("child", Lang.createBlock(statement)), "}")));
        var paramsAndValue = new FirstRule(params, ")", content);
        var methodRule = new TypeRule("method", new FirstRule(definition, "(", paramsAndValue));

        var classMember = new OrRule(List.of(
                declaration,
                methodRule
        ));

        var modifiers = Lang.createModifiersRule();
        return new TypeRule("class", new FirstRule(modifiers, "class ", new FirstRule(new StripRule(new ExtractStringRule("name")), "{", new RightRule(new ExtractNodeRule("child", Lang.createBlock(classMember)), "}"))));
    }

    private static TypeRule createConstructorRule(LazyRule value) {
        return new TypeRule("constructor", new LeftRule("new ", new RightRule(Lang.createSplitter(value), ")")));
    }

    private static Rule createDefinitionHeaderRule() {
        var type = Lang.createTypeRule();
        return createDefinitionHeaderRule(type);
    }

    private static Rule createDefinitionHeaderRule(Rule type) {
        var modifiers = Lang.createModifiersRule();
        var withoutModifiers = new ExtractNodeRule("type", type);
        var withModifiers = new LastRule(modifiers, " ", withoutModifiers);
        var anyModifiers = new OrRule(List.of(withModifiers, withoutModifiers));
        return new LastRule(anyModifiers, " ", new StripRule(new SymbolRule(new ExtractStringRule("name"))));
    }

    private static void initValues(LazyRule value, TypeRule constructor, TypeRule invocation) {
        value.setRule(new OrRule(List.of(
                new TypeRule("string", new LeftRule("\"", new RightRule(new ExtractStringRule("value"), "\""))),
                new TypeRule("char", new LeftRule("'", new RightRule(new ExtractStringRule("value"), "'"))),
                new TypeRule("lambda", new FirstRule(new StripRule(new ExtractStringRule("param-name")), "->", new StripRule(new ExtractNodeRule("value", value)))),
                new TypeRule("ternary", new FirstRule(
                        new StripRule(new ExtractNodeRule("condition", value)), "?",
                        new FirstRule(
                                new StripRule(new ExtractNodeRule("true", value)), ":",
                                new StripRule(new ExtractNodeRule("false", value))))),
                constructor,
                invocation,
                new TypeRule("access", new LastRule(new ExtractNodeRule("parent", value), ".", new StripRule(new SymbolRule(new ExtractStringRule("child"))))),
                new TypeRule("symbol", new SymbolRule(new ExtractStringRule("value"))),
                new TypeRule("number", new NumberRule(new ExtractStringRule("value"))),
                createOperator("equals", "==", value),
                createOperator("add", "+", value),
                createOperator("greater-than", ">", value)
        )));
    }

    private static TypeRule createOperator(String name, String slice, Rule value) {
        return new TypeRule(name, new FirstRule(new StripRule(new ExtractNodeRule("left", value)), slice, new StripRule(new ExtractNodeRule("right", value))));
    }

}
