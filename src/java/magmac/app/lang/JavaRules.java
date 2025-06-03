package magmac.app.lang;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.api.collect.list.Lists;
import magmac.api.iter.Iters;
import magmac.api.iter.collect.ListCollector;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.ContextRule;
import magmac.app.compile.rule.ExactRule;
import magmac.app.compile.rule.FilterRule;
import magmac.app.compile.rule.LocatingRule;
import magmac.app.compile.rule.NodeListRule;
import magmac.app.compile.rule.NodeRule;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.PrefixRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.Splitter;
import magmac.app.compile.rule.StringRule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.compile.rule.divide.Divider;
import magmac.app.compile.rule.divide.FoldingDivider;
import magmac.app.compile.rule.fold.DelimitedFolder;
import magmac.app.compile.rule.split.DividingSplitter;
import magmac.app.lang.java.JavaDeserializers;
import magmac.app.lang.java.JavaLang;
import magmac.app.lang.node.Arguments;
import magmac.app.lang.java.JavaNamespacedNode;
import magmac.app.lang.node.Modifier;
import magmac.app.lang.node.MultipleCaseValue;
import magmac.app.lang.node.Operator;
import magmac.app.lang.node.Parameters;
import magmac.app.lang.node.SingleCaseValue;
import magmac.app.lang.node.StructureMembers;

public final class JavaRules {
    public static Rule createConstructionRule() {
        return new TypeRule("construction", new StripRule(new PrefixRule("new ", new NodeRule("type", JavaLang.JavaTypes.createTypeRule()))));
    }

    public static Rule createInvokableRule(Rule value) {
        Rule childRule = new OrRule(Lists.of(JavaRules.createConstructionRule(), value));
        Rule caller = new NodeRule("caller", new SuffixRule(childRule, "("));
        Rule arguments = Arguments.createArgumentsRule(value);
        Splitter splitter = DividingSplitter.Last(new FoldingDivider(new InvocationFolder()), "");
        return new TypeRule("invokable", new StripRule(new SuffixRule(new LocatingRule(caller, splitter, arguments), ")")));
    }

    public static Rule createRootSegmentRule() {
        Rule classMemberRule = StructureMembers.createClassMemberRule();
        return new OrRule(Lists.of(
                createWhitespaceRule(),
                JavaNamespacedNode.createNamespacedRule("package"),
                JavaNamespacedNode.createNamespacedRule("import"),
                JavaRules.createStructureRule("record", classMemberRule),
                JavaRules.createStructureRule("interface", classMemberRule),
                JavaRules.createStructureRule("class", classMemberRule),
                JavaRules.createStructureRule("enum", classMemberRule)
        ));
    }

    public static Rule createRule() {
        return new TypeRule("root", CommonLang.Statements("children", JavaRules.createRootSegmentRule()));
    }

    public static Rule createStructureRule(String keyword, Rule structureMember) {
        Rule name = new StripRule(FilterRule.Symbol(new StringRule("name")));
        Rule beforeContent = attachTypeParams(name);

        Rule withParameters = new OrRule(Lists.of(
                new StripRule(new SuffixRule(LocatingRule.First(beforeContent, "(", Parameters.createParametersRule(createDefinitionRule())), ")")),
                beforeContent
        ));

        Rule type = JavaLang.JavaTypes.createTypeRule();
        Rule extended = NodeListRule.createNodeListRule("extended", new ValueFolder(), type);
        Rule withEnds = new OrRule(Lists.of(
                LocatingRule.First(withParameters, " extends ", extended),
                withParameters
        ));

        Rule implemented = NodeListRule.createNodeListRule("implemented", new ValueFolder(), type);
        Rule withImplements = new OrRule(Lists.of(
                new ContextRule("With implements", LocatingRule.First(withEnds, "implements", implemented)),
                new ContextRule("Without implements", withEnds)
        ));

        Rule withPermits = new OrRule(Lists.of(
                LocatingRule.Last(withImplements, " permits ", NodeListRule.Values("variants", type)),
                withImplements
        ));

        Rule afterKeyword = LocatingRule.First(withPermits, "{", new StripRule(new SuffixRule(CommonLang.Statements("children", structureMember), "}")));
        return new TypeRule(keyword, LocatingRule.First(Modifier.createModifiersRule(), keyword + " ", afterKeyword));
    }

    public static Rule createAccessRule(String type, String infix, LazyRule value) {
        Rule property = CommonRules.createSymbolRule("property");
        return new TypeRule(type, LocatingRule.Last(new NodeRule("receiver", value), infix, property));
    }

    public static Option<CompileResult<JavaLang.JavaLambdaValueContent>> deserializeLambdaValueContent(Node node) {
        return Destructors.destructWithType("value", node).map(destructor -> {
            return destructor.withNode("value", JavaDeserializers::deserializeJavaOrError).complete(JavaLang.JavaLambdaValueContent::new);
        });
    }

    public static Rule createYieldRule(Rule value) {
        return new TypeRule("yield", new StripRule(new PrefixRule("yield ", new NodeRule("value", value))));
    }

    public static Rule createLambdaRule(LazyRule value, Rule functionSegment, String infix, Rule definition) {
        Rule afterInfix = new OrRule(Lists.of(
                new TypeRule("block", new StripRule(new PrefixRule("{", new SuffixRule(CommonLang.Statements("children", functionSegment), "}")))),
                new TypeRule("value", new NodeRule("value", value))
        ));

        Rule parameters = JavaRules.createLambdaParameterRule(definition);
        Rule withParentheses = new TypeRule("multiple", new StripRule(new PrefixRule("(", new SuffixRule(parameters, ")"))));
        Rule withoutParentheses = CommonRules.createSymbolRule();

        Rule header = new NodeRule("header", new OrRule(Lists.of(
                withParentheses,
                withoutParentheses
        )));

        return new TypeRule("lambda", LocatingRule.First(header, infix, new NodeRule("content", afterInfix)));
    }

    public static Rule createStatementRule(Rule rule) {
        NodeRule child = new NodeRule("child", rule);
        return new TypeRule("statement", new StripRule(new SuffixRule(child, ";")));
    }

    public static Rule createLambdaParameterRule(Rule definition) {
        return NodeListRule.createNodeListRule("parameters", new ValueFolder(), new OrRule(Lists.of(
                definition,
                CommonRules.createSymbolRule()
        )));
    }

    public static Rule createBlockHeaderRule(Rule value, Rule definition) {
        return new OrRule(Lists.of(
                new TypeRule("else", new StripRule(new ExactRule("else"))),
                new TypeRule("try", new StripRule(new ExactRule("try"))),
                createConditionalRule("if", value),
                createConditionalRule("while", value),
                new TypeRule("catch", new StripRule(new PrefixRule("catch", new StripRule(new PrefixRule("(", new SuffixRule(new NodeRule("definition", definition), ")"))))))
        ));
    }

    public static Rule createBlockRule(LazyRule functionSegmentRule, Rule value, Rule definition) {
        Rule header = new NodeRule("header", createBlockHeaderRule(value, definition));
        return createBlockRule0(header, functionSegmentRule);
    }

    public static TypeRule createBlockRule0(Rule header, Rule functionSegmentRule) {
        Rule children = CommonLang.Statements("children", functionSegmentRule);
        Splitter first = DividingSplitter.First(new FoldingDivider(new BlockFolder()), "");
        Rule childRule = new LocatingRule(new SuffixRule(header, "{"), first, children);
        return new TypeRule("block", new StripRule(new SuffixRule(childRule, "}")));
    }

    public static Rule createConditionalRule(String type, Rule value) {
        Rule condition = new NodeRule("condition", value);
        Rule childRule = new StripRule(new PrefixRule("(", new SuffixRule(condition, ")")));
        return new TypeRule(type, new StripRule(new PrefixRule(type, childRule)));
    }

    public static Rule createReturnRule(Rule value) {
        return new TypeRule("return", new StripRule(new PrefixRule("return ", new NodeRule("child", value))));
    }

    public static Rule createWhitespaceRule() {
        return new TypeRule("whitespace", new StripRule(new ExactRule("")));
    }

    public static Rule createStringRule() {
        return new TypeRule("string", new StripRule(new PrefixRule("\"", new SuffixRule(new StringRule("value"), "\""))));
    }

    public static Rule createOperationRule(Operator operator, LazyRule value) {
        return new TypeRule(operator.type(), LocatingRule.First(new NodeRule("left", value), operator.text(), new NodeRule("right", value)));
    }

    public static Rule createCharRule() {
        return new TypeRule("char", new StripRule(new PrefixRule("'", new SuffixRule(new StringRule("value"), "'"))));
    }

    public static Rule createNumberRule() {
        return new TypeRule("number", new StripRule(FilterRule.Number(new StringRule("value"))));
    }

    public static TypeRule createNotRule(LazyRule value) {
        return new TypeRule("not", new StripRule(new PrefixRule("!", new NodeRule("child", value))));
    }

    public static Rule createIndexRule(LazyRule value) {
        NodeRule parent = new NodeRule("parent", value);
        NodeRule argument = new NodeRule("argument", value);
        return new TypeRule("index", new StripRule(new SuffixRule(LocatingRule.First(parent, "[", argument), "]")));
    }

    public static LazyRule initValueRule(Rule segment, LazyRule value, String lambdaInfix, Rule definition) {
        return value.set(new OrRule(getValueRules(segment, value, lambdaInfix, definition)));
    }

    private static List<Rule> getValueRules(Rule functionSegment, LazyRule value, String lambdaInfix, Rule definition) {
        List<Rule> ruleList = Lists.of(
                createSwitchRule(functionSegment, value),
                createLambdaRule(value, functionSegment, lambdaInfix, definition),
                createNotRule(value),
                createCharRule(),
                createStringRule(),
                createInvokableRule(value),
                createIndexRule(value),
                createNumberRule(),
                CommonRules.createSymbolRule(),
                createAccessRule("data-access", ".", value),
                createAccessRule("method-access", "::", value)
        );

        List<Rule> operatorLists = Iters.fromValues(Operator.values())
                .map(operator -> createOperationRule(operator, value))
                .collect(new ListCollector<>());

        return ruleList.addAllLast(operatorLists);
    }

    private static TypeRule createSwitchRule(Rule functionSegmentRule, Rule value) {
        NodeRule value1 = new NodeRule("value", value);
        PrefixRule header = new PrefixRule("switch", new StripRule(new PrefixRule("(", new SuffixRule(value1, ")"))));
        return new TypeRule("switch", createBlockRule0(new StripRule(header), functionSegmentRule));
    }

    public static Rule createDefinitionRule() {
        Rule modifiers = Modifier.createModifiersRule();
        Rule annotations = NodeListRule.createNodeListRule("annotations", new DelimitedFolder('\n'), new StripRule(new PrefixRule("@", new StringRule("value"))));
        Rule beforeTypeParams = new OrRule(Lists.of(
                LocatingRule.Last(annotations, "\n", modifiers),
                modifiers
        ));

        Rule leftRule1 = attachTypeParams(beforeTypeParams);

        Rule rightRule = new NodeRule("type", JavaLang.JavaTypes.createTypeRule());
        Divider divider = new FoldingDivider(new TypeSeparatorFolder());
        Splitter splitter = DividingSplitter.Last(divider, " ");
        Rule leftRule = new LocatingRule(leftRule1, splitter, rightRule);
        Rule stripRule = new StripRule(LocatingRule.Last(leftRule, " ", new StripRule(FilterRule.Symbol(new StringRule("name")))));
        return new TypeRule("definition", stripRule);
    }

    public static Rule attachTypeParams(Rule beforeTypeParams) {
        Rule typeParams = NodeListRule.createNodeListRule("type-parameters", new ValueFolder(), new StringRule("value"));
        return new OptionNodeListRule("type-parameters",
                new StripRule(new SuffixRule(LocatingRule.First(beforeTypeParams, "<", typeParams), ">")),
                beforeTypeParams
        );
    }

    public static Rule createCaseRule(Rule value, Rule segment) {
        Rule typeRule = JavaLang.JavaTypes.createTypeRule();
        Rule name = new StripRule(new StringRule("name"));
        Rule last = LocatingRule.Last(new NodeRule("type", typeRule), " ", name);
        Rule definitions = new TypeRule("case-definition", new OrRule(Lists.of(
                new StripRule(last),
                name
        )));

        Rule beforeArrow = NodeListRule.Values("definitions", definitions);
        OrRule children = new OrRule(Lists.of(
                SingleCaseValue.createRule(value),
                MultipleCaseValue.createRule(segment)
        ));

        Rule childRule = LocatingRule.First(beforeArrow, "->", new NodeRule("value", children));
        return new TypeRule("case", new StripRule(new PrefixRule("case", childRule)));
    }

    public static Rule createAssignmentRule(Rule definition, Rule value) {
        Rule before = new NodeRule("destination", new OrRule(Lists.of(
                definition,
                value
        )));

        Rule source = new NodeRule("source", value);
        return new TypeRule("assignment", LocatingRule.First(before, "=", source));
    }
}
