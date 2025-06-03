package magmac.app.lang.node;

import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.LocatingRule;
import magmac.app.compile.rule.PrefixRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StringRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.CommonLang;
import magmac.app.lang.Serializable;
import magmac.app.lang.TypescriptLang;
import magmac.app.lang.java.JavaDefinition;

public final class TypescriptStructureNode implements TypeScriptRootSegment {
    private final TypescriptStructureType type;
    private final StructureValue<TypeScriptType, TypescriptStructureMember> value;

    public TypescriptStructureNode(TypescriptStructureType type, StructureValue<TypeScriptType, TypescriptStructureMember> structureNode) {
        this.type = type;
        this.value = structureNode;
    }

    public static Rule createStructureRule(String type) {
        Rule children = CommonLang.Statements("members", TypescriptLang.createStructureMemberRule());
        Rule name = new StringRule("name");
        Rule afterKeyword = LocatingRule.First(JavaDefinition.attachTypeParams(name), " {", new SuffixRule(children, "\n}\n"));
        return new TypeRule(type, new PrefixRule("export " + type + " ", afterKeyword));
    }

    private Node serializeImplementsParams() {
        return this.value.maybeImplemented()
                .map(implemented -> new MapNode().withNodeListSerialized("implemented", implemented))
                .orElse(new MapNode());
    }

    private Node serializeExtendedParams() {
        return this.value.maybeExtended()
                .map(extended -> new MapNode().withNodeListSerialized("extended", extended))
                .orElse(new MapNode());
    }

    private Node serializeTypeParams() {
        return this.value.maybeTypeParams()
                .map(typeParams -> new MapNode().withNodeListSerialized("type-parameters", typeParams))
                .orElse(new MapNode());
    }

    @Override
    public Node serialize() {
        return new MapNode(this.type.name().toLowerCase())
                .withString("name", this.value.name())
                .withNodeListAndSerializer("modifiers", this.value.modifiers(), Serializable::serialize)
                .withNodeListAndSerializer("members", this.value.members(), Serializable::serialize)
                .merge(this.serializeTypeParams())
                .merge(this.serializeExtendedParams())
                .merge(this.serializeImplementsParams());
    }
}
