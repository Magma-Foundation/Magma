package magmac.app.lang.node;

import magmac.api.Option;
import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
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
import magmac.app.lang.Deserializers;
import magmac.app.lang.OptionNodeListRule;
import magmac.app.lang.TypeSeparatorFolder;
import magmac.app.lang.ValueFolder;

public final class JavaDefinition implements Parameter, Assignable, MethodHeader {
    private final Definition<JavaType> definition;

    public JavaDefinition(Definition<JavaType> definition) {
        this.definition = definition;
    }

    public static CompileResult<JavaDefinition> deserialize(Node node) {
        return Definition.deserializeWithDestructor(node).mapValue(JavaDefinition::new);
    }

    public static Option<CompileResult<JavaDefinition>> deserializeTyped(Node node) {
        return Deserializers.deserializeWithType(node, "definition")
                .map(deserialize -> Definition.deserialize0(deserialize).mapValue(JavaDefinition::new));
    }

    public static Rule creaeteRule() {
        Rule modifiers = Modifier.createModifiersRule();
        Rule annotations = NodeListRule.createNodeListRule("annotations", new DelimitedFolder('\n'), new StripRule(new PrefixRule("@", new StringRule("value"))));
        Rule beforeTypeParams = new OrRule(Lists.of(
                LocatingRule.Last(annotations, "\n", modifiers),
                modifiers
        ));

        Rule leftRule1 = JavaDefinition.attachTypeParams(beforeTypeParams);

        Rule rightRule = new NodeRule("type", Types.createTypeRule());
        Divider divider = new FoldingDivider(new TypeSeparatorFolder());
        Splitter splitter = DividingSplitter.Last(divider, " ");
        Rule leftRule = new LocatingRule(leftRule1, splitter, rightRule);
        Rule stripRule = new StripRule(LocatingRule.Last(leftRule, " ", new StripRule(FilterRule.Symbol(new StringRule("name")))));
        return new TypeRule("definition", stripRule);
    }

    static Rule attachTypeParams(Rule beforeTypeParams) {
        Rule typeParams = NodeListRule.createNodeListRule("type-parameters", new ValueFolder(), new StringRule("value"));
        return new OptionNodeListRule("type-parameters",
                new StripRule(new SuffixRule(LocatingRule.First(beforeTypeParams, "<", typeParams), ">")),
                beforeTypeParams
        );
    }

    @Override
    public Node serialize() {
        return this.definition.serialize();
    }
}
