package magmac.app.lang.node;

import magmac.app.compile.node.Node;
import magmac.app.compile.rule.LocatingRule;
import magmac.app.compile.rule.PrefixRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StringRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.CommonLang;
import magmac.app.lang.JavaLang;
import magmac.app.lang.TypescriptLang;

public final class TypescriptStructureNode implements TypeScriptRootSegment {
    private final TypescriptStructureType type;
    private final StructureValue<TypescriptStructureMember> value;

    public TypescriptStructureNode(TypescriptStructureType type, StructureValue<TypescriptStructureMember> structureNode) {
        this.type = type;
        this.value = structureNode;
    }

    public static Rule createClassRule(String type) {
        Rule children = CommonLang.Statements("members", TypescriptLang.createStructureMemberRule());
        Rule name = new StringRule("name");
        Rule afterKeyword = LocatingRule.First(JavaLang.attachTypeParams(name), " {", new SuffixRule(children, "\n}\n"));
        return new TypeRule(type, new PrefixRule("export " + type + " ", afterKeyword));
    }

    @Override
    public Node serialize() {
        return this.value.serialize(this.type.name().toLowerCase());
    }
}
