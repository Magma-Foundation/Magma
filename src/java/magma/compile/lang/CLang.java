package magma.compile.lang;

import jvm.collect.list.Lists;
import magma.compile.rule.Rule;
import magma.compile.rule.divide.CharDivider;
import magma.compile.rule.divide.DecoratedFolder;
import magma.compile.rule.divide.FoldingDivider;
import magma.compile.rule.divide.StatementFolder;
import magma.compile.rule.text.EmptyRule;
import magma.compile.rule.text.PrefixRule;
import magma.compile.rule.text.StringRule;
import magma.compile.rule.text.SuffixRule;
import magma.compile.rule.tree.NodeListRule;
import magma.compile.rule.tree.NodeRule;
import magma.compile.rule.tree.OrRule;
import magma.compile.rule.tree.TypeRule;

public class CLang {
    public static Rule createCRootRule() {
        return new NodeListRule("children", new FoldingDivider(new DecoratedFolder(new StatementFolder())), createCRootSegmentRule());
    }

    private static Rule createCRootSegmentRule() {
        return new OrRule(Lists.of(
                createIncludeRule(),
                createStructRule(),
                createFunctionRule()
        ));
    }

    private static TypeRule createFunctionRule() {
        return new TypeRule("function", new PrefixRule("void temp(){\n}\n", new EmptyRule()));
    }

    private static TypeRule createStructRule() {
        StringRule name = new StringRule("name");
        return new TypeRule("struct", new PrefixRule("struct ", CommonLang.createContentRule(name, createStructMemberRule())));
    }

    private static OrRule createStructMemberRule() {
        return new OrRule(Lists.of(
                new TypeRule("definition", CommonLang.createDefinitionRule(createTypeRule())),
                new TypeRule("functional-definition", new NodeRule("returns", createTypeRule()))
        ));
    }

    private static Rule createTypeRule() {
        return new OrRule(Lists.of(
                CommonLang.createSymbolTypeRule()
        ));
    }

    private static Rule createIncludeRule() {
        NodeListRule path = new NodeListRule("path", new CharDivider('/'), new StringRule("value"));
        return new TypeRule("include", new PrefixRule("#include \"", new SuffixRule(path, ".h\"\n")));
    }
}
