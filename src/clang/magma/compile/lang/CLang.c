package magma.compile.lang;class package magma.compile.lang;{package magma.compile.lang;

import jvm.collect.list.Lists;class import jvm.collect.list.Lists;{

import jvm.collect.list.Lists;
import magma.compile.rule.DivideRule;class import magma.compile.rule.DivideRule;{
import magma.compile.rule.DivideRule;
import magma.compile.rule.PrefixRule;class import magma.compile.rule.PrefixRule;{
import magma.compile.rule.PrefixRule;
import magma.compile.rule.Rule;class import magma.compile.rule.Rule;{
import magma.compile.rule.Rule;
import magma.compile.rule.SuffixRule;class import magma.compile.rule.SuffixRule;{
import magma.compile.rule.SuffixRule;
import magma.compile.rule.OrRule;class import magma.compile.rule.OrRule;{
import magma.compile.rule.OrRule;
import magma.compile.rule.StringRule;class import magma.compile.rule.StringRule;{
import magma.compile.rule.StringRule;
import magma.compile.rule.TypeRule;class import magma.compile.rule.TypeRule;{
import magma.compile.rule.TypeRule;
import magma.compile.rule.divide.CharDivider;class import magma.compile.rule.divide.CharDivider;{
import magma.compile.rule.divide.CharDivider;
import magma.compile.rule.divide.StatementDivider;class import magma.compile.rule.divide.StatementDivider;{
import magma.compile.rule.divide.StatementDivider;

public class CLang {
    public static Rule createCRootRule() {
        return new DivideRule(new StatementDivider(), createCRootSegmentRule(), "children");
    }

    private static OrRule createCRootSegmentRule() {
        return new OrRule(Lists.of(
                createIncludeRule()
        ));
    }

    private static Rule createIncludeRule() {
        DivideRule path = new DivideRule(new CharDivider('/'), new StringRule("value"), "path");
        return new TypeRule("include",  new PrefixRule("#include \"", new SuffixRule(path, ".h\"\n")));
    }
}
class public class CLang {
    public static Rule createCRootRule() {
        return new DivideRule(new StatementDivider(), createCRootSegmentRule(), "children");
    }

    private static OrRule createCRootSegmentRule() {
        return new OrRule(Lists.of(
                createIncludeRule()
        ));
    }

    private static Rule createIncludeRule() {
        DivideRule path = new DivideRule(new CharDivider('/'), new StringRule("value"), "path");
        return new TypeRule("include",  new PrefixRule("#include \"", new SuffixRule(path, ".h\"\n")));
    }
}{

public class CLang {
    public static Rule createCRootRule() {
        return new DivideRule(new StatementDivider(), createCRootSegmentRule(), "children");
    }

    private static OrRule createCRootSegmentRule() {
        return new OrRule(Lists.of(
                createIncludeRule()
        ));
    }

    private static Rule createIncludeRule() {
        DivideRule path = new DivideRule(new CharDivider('/'), new StringRule("value"), "path");
        return new TypeRule("include",  new PrefixRule("#include \"", new SuffixRule(path, ".h\"\n")));
    }
}
