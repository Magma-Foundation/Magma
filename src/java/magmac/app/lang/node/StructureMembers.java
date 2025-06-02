package magmac.app.lang.node;

import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.LazyRule;
import magmac.app.lang.MutableLazyRule;
import magmac.app.lang.Deserializers;

final class StructureMembers {
    public static CompileResult<JavaStructureMember> deserialize(Node node) {
        return Deserializers.orError("structure-members", node, Lists.of(
                Deserializers.wrap(Whitespace::deserialize),
                Deserializers.wrap(JavaMethod::deserialize),
                Deserializers.wrap(StructureStatement::deserialize),
                Deserializers.wrap(EnumValues::deserialize)
        ));
    }

    public static Rule createClassMemberRule() {
        LazyRule functionSegmentRule = new MutableLazyRule();
        LazyRule valueLazy = new MutableLazyRule();
        LazyRule value = Values.initValueRule(functionSegmentRule, valueLazy, "->", JavaDefinition.createRule());
        Rule functionSegment = FunctionSegments.initFunctionSegmentRule(functionSegmentRule, value, JavaDefinition.createRule());
        return new OrRule(Lists.of(
                Whitespace.createWhitespaceRule(),
                StructureStatement.createStructureStatementRule(new TypeRule("definition", JavaDefinition.createRule()), value),
                JavaMethod.createMethodRule(functionSegment),
                EnumValues.createEnumValuesRule(value)
        ));
    }
}
