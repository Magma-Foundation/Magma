package magmac.app.lang.node;

/**
 * Deserializes members inside a class or interface body such as methods and
 * nested structures.
 */

import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.Deserializers;
import magmac.app.lang.java.JavaDeserializers;
import magmac.app.lang.JavaRules;
import magmac.app.lang.LazyRule;
import magmac.app.lang.MutableLazyRule;
import magmac.app.lang.java.JavaEnumValues;
import magmac.app.lang.java.JavaLang;
import magmac.app.lang.java.JavaMethod;
import magmac.app.lang.java.JavaStructureMember;
import magmac.app.lang.java.JavaStructureStatement;

public final class StructureMembers {
    public static CompileResult<JavaStructureMember> deserialize(Node node) {
        return Deserializers.orError("structure-members", node, Lists.of(
                Deserializers.wrap(JavaDeserializers::deserializeWhitespace),
                Deserializers.wrap(JavaMethod::deserialize),
                Deserializers.wrap(JavaStructureStatement::deserialize),
                Deserializers.wrap(JavaEnumValues::deserialize),
                Deserializers.wrap(new JavaLang.JavaStructureNodeDeserializer(JavaLang.JavaStructureType.Class)),
                Deserializers.wrap(new JavaLang.JavaStructureNodeDeserializer(JavaLang.JavaStructureType.Interface)),
                Deserializers.wrap(new JavaLang.JavaStructureNodeDeserializer(JavaLang.JavaStructureType.Record)),
                Deserializers.wrap(new JavaLang.JavaStructureNodeDeserializer(JavaLang.JavaStructureType.Enum))
        ));
    }

    public static Rule createClassMemberRule() {
        LazyRule classMemberRule = new MutableLazyRule();
        LazyRule functionSegmentRule = new MutableLazyRule();
        LazyRule valueLazy = new MutableLazyRule();
        var value = JavaRules.initValueRule(functionSegmentRule, valueLazy, "->", JavaRules.createDefinitionRule());
        var functionSegment = FunctionSegments.initFunctionSegmentRule(functionSegmentRule, value, JavaRules.createDefinitionRule());
        return classMemberRule.set(new OrRule(Lists.of(
                JavaRules.createTypedWhitespaceRule(),
                JavaStructureStatement.createStructureStatementRule(new TypeRule("definition", JavaRules.createDefinitionRule()), value),
                JavaMethod.createMethodRule(functionSegment),
                JavaEnumValues.createEnumValuesRule(value),
                JavaRules.createStructureRule("record", classMemberRule),
                JavaRules.createStructureRule("interface", classMemberRule),
                JavaRules.createStructureRule("class", classMemberRule),
                JavaRules.createStructureRule("enum", classMemberRule)
        )));
    }
}
