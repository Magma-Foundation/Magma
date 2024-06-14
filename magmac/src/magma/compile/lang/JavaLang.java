package magma.compile.lang;

import magma.Main;
import magma.compile.rule.EmptyRule;
import magma.compile.rule.MembersRule;
import magma.compile.rule.OrRule;
import magma.compile.rule.Rule;
import magma.compile.rule.TypeRule;
import magma.compile.rule.result.FirstRule;
import magma.compile.rule.result.LastRule;
import magma.compile.rule.text.LeftRule;
import magma.compile.rule.text.RightRule;
import magma.compile.rule.text.StripRule;
import magma.compile.rule.text.extract.ExtractNodeRule;
import magma.compile.rule.text.extract.ExtractStringListRule;
import magma.compile.rule.text.extract.ExtractStringRule;

import java.util.List;

public class JavaLang {
    public static Rule createClassRule() {
        var modifiers = new ExtractStringListRule("modifiers", " ");
        var name = new StripRule(new ExtractStringRule("name"));
        var block = createBlock(createClassMemberRule());
        var content = new ExtractNodeRule("content", block);

        return new TypeRule("class", new FirstRule(new StripRule(modifiers), Main.CLASS_KEYWORD_WITH_SPACE, new StripRule(new FirstRule(name, "{", new StripRule(new RightRule(content, "}"))))));
    }

    private static TypeRule createBlock(Rule memberRule) {
        var children = new MembersRule("children", new StripRule(memberRule));
        return new TypeRule("block", children);
    }

    private static Rule createClassMemberRule() {
        return new OrRule(List.of(
                createMethodRule(),
                createDeclarationRule()
        ));
    }

    private static TypeRule createAnyRule() {
        return new TypeRule("any", new ExtractStringRule("content"));
    }

    private static Rule createMethodRule() {
        var header = new StripRule(createDefinitionRule());
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
        var modifiersAndType = new LastRule(new ExtractStringListRule("modifiers", " "), " ", new ExtractStringRule("type"));
        var withoutParams = new LastRule(modifiersAndType, " ", new ExtractStringRule("name"));
        var withParams = new LastRule(withoutParams, "(", new RightRule(new ExtractStringRule("params"), ")"));
        return new OrRule(List.of(withParams, withoutParams));
    }

    public static TypeRule createRootRule() {
        var childRule = new OrRule(List.of(
                new TypeRule("whitespace", new EmptyRule()),
                new TypeRule("package", new LeftRule("package ", new ExtractStringListRule("namespace", "."))),
                new TypeRule("import", new LeftRule("import ", new ExtractStringRule("value"))),
                createClassRule()));

        return new TypeRule("block", new MembersRule("children", new StripRule(childRule)));
    }
}
