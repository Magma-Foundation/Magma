package magmac.app.lang.node;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.InitialDestructor;
import magmac.app.compile.node.MapNode;
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
import magmac.app.lang.Serializable;
import magmac.app.lang.TypeSeparatorFolder;
import magmac.app.lang.ValueFolder;

public record Definition(
        String name,
        Type type,
        List<Modifier> modifiers,
        Option<List<Annotation>> annotations,
        Option<List<TypeParam>> typeParams
) implements Parameter, Assignable, MethodHeader {
    public static CompileResult<Definition> deserializeError(Node node) {
        return Definition.complete(Deserializers.destruct(node));
    }

    private static CompileResult<Definition> complete(InitialDestructor deserialize) {
        return deserialize.withString("name")
                .withNode("type", Types::deserialize)
                .withNodeList("modifiers", Modifier::deserialize)
                .withNodeListOptionally("annotations", Annotation::deserialize)
                .withNodeListOptionally("type-parameters", TypeParam::deserialize)
                .complete((result) -> new Definition(
                        result.left().left().left().left(),
                        result.left().left().left().right(),
                        result.left().left().right(),
                        result.left().right(),
                        result.right()));
    }

    public static Option<CompileResult<Definition>> deserialize(Node node) {
        return Deserializers.deserializeWithType(node, "definition").map(Definition::complete);
    }

    public static Rule createDefinitionRule() {
        Rule modifiers = Modifier.createModifiersRule();
        Rule annotations = NodeListRule.createNodeListRule("annotations", new DelimitedFolder('\n'), new StripRule(new PrefixRule("@", new StringRule("value"))));
        Rule beforeTypeParams = new OrRule(Lists.of(
                LocatingRule.Last(annotations, "\n", modifiers),
                modifiers
        ));

        Rule leftRule1 = Definition.attachTypeParams(beforeTypeParams);

        Rule rightRule = new NodeRule("type", Types.createTypeRule());
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

    @Override
    public Node serialize() {
        return new MapNode("definition")
                .withString("name", name)
                .withNodeAndSerializer("type", type, Serializable::serialize);
    }
}
