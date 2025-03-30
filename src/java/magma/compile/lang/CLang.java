package magma.compile.lang;

import jvm.collect.list.Lists;
import magma.compile.rule.tree.NodeListRule;
import magma.compile.rule.text.PrefixRule;
import magma.compile.rule.Rule;
import magma.compile.rule.text.SuffixRule;
import magma.compile.rule.tree.OrRule;
import magma.compile.rule.text.StringRule;
import magma.compile.rule.tree.TypeRule;
import magma.compile.rule.divide.CharDivider;
import magma.compile.rule.divide.StatementDivider;

public class CLang {
    public static Rule createCRootRule() {
        return new NodeListRule("children", new StatementDivider(), createCRootSegmentRule());
    }

    private static Rule createCRootSegmentRule() {
        return new OrRule(Lists.of(
                createIncludeRule(),
                createStructRule()
        ));
    }

    private static TypeRule createStructRule() {
        StringRule name = new StringRule("name");
        return new TypeRule("struct", new PrefixRule("struct ", CommonLang.withContent(name, createStructMemberRule())));
    }

    private static OrRule createStructMemberRule() {
        return new OrRule(Lists.of());
    }

    private static Rule createIncludeRule() {
        NodeListRule path = new NodeListRule("path", new CharDivider('/'), new StringRule("value"));
        return new TypeRule("include",  new PrefixRule("#include \"", new SuffixRule(path, ".h\"\n")));
    }
}
