package magma.compile.lang;

import magma.Main;
import magma.compile.rule.EmptyRule;
import magma.compile.rule.MembersRule;
import magma.compile.rule.OrRule;
import magma.compile.rule.Rule;
import magma.compile.rule.TypeRule;
import magma.compile.rule.result.SplitAtSliceRule;
import magma.compile.rule.text.LeftRule;
import magma.compile.rule.text.RightRule;
import magma.compile.rule.text.StripRule;
import magma.compile.rule.text.extract.ExtractStringListRule;
import magma.compile.rule.text.extract.ExtractStringRule;

import java.util.List;

public class JavaLang {
    public static Rule createClassRule() {
        var modifiers = new ExtractStringListRule("modifiers", " ");
        var name = new StripRule(new ExtractStringRule("name"));
        var content = new MembersRule("content", createClassMemberRule());

        var splitAtSliceRule = new SplitAtSliceRule(new StripRule(modifiers), Main.CLASS_KEYWORD_WITH_SPACE, new StripRule(new SplitAtSliceRule(name, "{", new StripRule(new RightRule(content, "}")))));
        return new TypeRule("class", splitAtSliceRule);
    }

    private static Rule createClassMemberRule() {
        return new OrRule(List.of(
                new TypeRule("any", new ExtractStringRule("content"))
        ));
    }

    public static TypeRule createRootRule() {
        var childRule = new OrRule(List.of(
                new TypeRule("whitespace", new EmptyRule()),
                new TypeRule("package", new LeftRule("package ", new ExtractStringListRule("namespace", "."))),
                new TypeRule("import", new LeftRule("import ", new ExtractStringRule("value"))),
                createClassRule()));

        return new TypeRule("root", new MembersRule("children", new StripRule(childRule)));
    }
}
