package magmac.app.lang.node;

import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.Deserializers;
import magmac.app.lang.JavaRules;
import magmac.app.lang.LazyRule;
import magmac.app.lang.MutableLazyRule;

public final class StructureMembers {
    public static CompileResult<JavaStructureMember> deserialize(Node node) {
        return Deserializers.orError("structure-members", node, Lists.of(
                Deserializers.wrap(Whitespace::deserialize),
                Deserializers.wrap(JavaMethod::deserialize),
                Deserializers.wrap(StructureStatement::deserialize),
                Deserializers.wrap(EnumValues::deserialize),
                Deserializers.wrap(new JavaStructureNodeDeserializer(JavaStructureType.Class)),
                Deserializers.wrap(new JavaStructureNodeDeserializer(JavaStructureType.Interface)),
                Deserializers.wrap(new JavaStructureNodeDeserializer(JavaStructureType.Record)),
                Deserializers.wrap(new JavaStructureNodeDeserializer(JavaStructureType.Enum))
        ));
    }

    public static Rule createClassMemberRule() {
        LazyRule classMemberRule = new MutableLazyRule();
        LazyRule functionSegmentRule = new MutableLazyRule();
        LazyRule valueLazy = new MutableLazyRule();
        LazyRule value = Values.initValueRule(functionSegmentRule, valueLazy, "->", JavaDefinition.createRule());
        Rule functionSegment = FunctionSegments.initFunctionSegmentRule(functionSegmentRule, value, JavaDefinition.createRule());
        return classMemberRule.set(new OrRule(Lists.of(
                Whitespace.createWhitespaceRule(),
                StructureStatement.createStructureStatementRule(new TypeRule("definition", JavaDefinition.createRule()), value),
                JavaMethod.createMethodRule(functionSegment),
                EnumValues.createEnumValuesRule(value),
                JavaRules.createStructureRule("record", classMemberRule),
                JavaRules.createStructureRule("interface", classMemberRule),
                JavaRules.createStructureRule("class", classMemberRule),
                JavaRules.createStructureRule("enum", classMemberRule)
        )));
    }
}
