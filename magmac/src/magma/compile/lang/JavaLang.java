package magma.compile.lang;

import magma.Main;
import magma.compile.rule.ContainsRule;
import magma.compile.rule.EmptyRule;
import magma.compile.rule.OrRule;
import magma.compile.rule.Rule;
import magma.compile.rule.SymbolRule;
import magma.compile.rule.TypeRule;
import magma.compile.rule.split.FirstRule;
import magma.compile.rule.split.LastRule;
import magma.compile.rule.split.MembersSplitter;
import magma.compile.rule.split.ParamSplitter;
import magma.compile.rule.split.SplitMultipleRule;
import magma.compile.rule.text.LeftRule;
import magma.compile.rule.text.RightRule;
import magma.compile.rule.text.StripRule;
import magma.compile.rule.text.extract.ExtractNodeRule;
import magma.compile.rule.text.extract.ExtractStringRule;
import magma.compile.rule.text.extract.QualifiedExtractStringListRule;
import magma.compile.rule.text.extract.SimpleExtractStringListRule;

import java.util.List;

public class JavaLang {
    public static Rule createClassRule() {
        var modifiers = new SimpleExtractStringListRule("modifiers", " ");
        var name = new StripRule(new ExtractStringRule("name"));
        var block = createBlock(createClassMemberRule());
        var content = new ExtractNodeRule("content", block);

        return new TypeRule("class", new FirstRule(new StripRule(modifiers), Main.CLASS_KEYWORD_WITH_SPACE, new StripRule(new FirstRule(name, "{", new StripRule(new RightRule(content, "}"))))));
    }

    private static TypeRule createBlock(Rule memberRule) {
        var children = new SplitMultipleRule(new MembersSplitter(), "", "children", new StripRule(memberRule));
        return new TypeRule("block", children);
    }

    private static Rule createClassMemberRule() {
        return new OrRule(List.of(
                createWhitespaceRule(),
                createMethodRule(),
                createDeclarationRule()
        ));
    }

    private static TypeRule createAnyRule() {
        return new TypeRule("any", new ExtractStringRule("content"));
    }

    private static Rule createMethodRule() {
        var definition = createDefinitionRule();
        var params = new SplitMultipleRule(new ParamSplitter(), ",", "params", new TypeRule("param", definition));

        var header = new LastRule(definition, "(", new RightRule(params, ")"));

        return new TypeRule("method", new FirstRule(header, "{", new RightRule(new ExtractNodeRule("content", createBlock(createStatementRule())), "}")));
    }

    private static Rule createStatementRule() {
        return new OrRule(
                List.of(createAnyRule())
        );
    }

    private static TypeRule createDeclarationRule() {
        var left = createDefinitionRule();
        var value = new StripRule(new ExtractStringRule("value"));
        return new TypeRule("declaration", new FirstRule(new StripRule(left), "=", value));
    }

    private static Rule createDefinitionRule() {
        var withoutModifiers = new SymbolRule(new ExtractStringRule("type"));
        var modifiersFilter = new ContainsRule(new ExtractStringRule(""),
                "public",
                "static",
                "final"
        );

        var withModifiers = new LastRule(new QualifiedExtractStringListRule("modifiers", " ", modifiersFilter), " ", withoutModifiers);
        var anyModifiers = new OrRule(List.of(withModifiers, withoutModifiers));

        return new LastRule(anyModifiers, " ", new ExtractStringRule("name"));
    }

    public static TypeRule createRootRule() {
        var childRule = new OrRule(List.of(
                createWhitespaceRule(),
                new TypeRule("package", new LeftRule("package ", new SimpleExtractStringListRule("namespace", "."))),
                new TypeRule("import", new LeftRule("import ", new ExtractStringRule("value"))),
                createClassRule()));

        return new TypeRule("block", new SplitMultipleRule(new MembersSplitter(), "", "children", new StripRule(childRule)));
    }

    private static TypeRule createWhitespaceRule() {
        return new TypeRule("whitespace", new EmptyRule());
    }
}
