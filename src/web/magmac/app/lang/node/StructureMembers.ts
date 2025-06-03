import { Lists } from "../../../../magmac/api/collect/list/Lists";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { OrRule } from "../../../../magmac/app/compile/rule/OrRule";
import { Rule } from "../../../../magmac/app/compile/rule/Rule";
import { TypeRule } from "../../../../magmac/app/compile/rule/TypeRule";
import { Deserializers } from "../../../../magmac/app/lang/Deserializers";
import { JavaDeserializers } from "../../../../magmac/app/lang/java/JavaDeserializers";
import { JavaRules } from "../../../../magmac/app/lang/JavaRules";
import { LazyRule } from "../../../../magmac/app/lang/LazyRule";
import { MutableLazyRule } from "../../../../magmac/app/lang/MutableLazyRule";
import { JavaEnumValues } from "../../../../magmac/app/lang/java/JavaEnumValues";
import { JavaLang } from "../../../../magmac/app/lang/java/JavaLang";
import { JavaMethod } from "../../../../magmac/app/lang/java/JavaMethod";
import { JavaStructureMember } from "../../../../magmac/app/lang/java/JavaStructureMember";
import { JavaStructureStatement } from "../../../../magmac/app/lang/java/JavaStructureStatement";
export class StructureMembers {
	public static deserialize( node : Node) : CompileResult<JavaStructureMember> {return Deserializers.orError( "structure-members", node, Lists.of( Deserializers.wrap( JavaDeserializers.deserializeWhitespace), Deserializers.wrap( JavaMethod.deserialize), Deserializers.wrap( JavaStructureStatement.deserialize), Deserializers.wrap( JavaEnumValues.deserialize), Deserializers.wrap( new JavaLang.JavaStructureNodeDeserializer( JavaLang.JavaStructureType.Class)), Deserializers.wrap( new JavaLang.JavaStructureNodeDeserializer( JavaLang.JavaStructureType.Interface)), Deserializers.wrap( new JavaLang.JavaStructureNodeDeserializer( JavaLang.JavaStructureType.Record)), Deserializers.wrap( new JavaLang.JavaStructureNodeDeserializer( JavaLang.JavaStructureType.Enum))));;}
	public static createClassMemberRule() : Rule { let classMemberRule : LazyRule=new MutableLazyRule( ); let functionSegmentRule : LazyRule=new MutableLazyRule( ); let valueLazy : LazyRule=new MutableLazyRule( ); let value : var=JavaRules.initValueRule( functionSegmentRule, valueLazy, "->", JavaRules.createDefinitionRule( )); let functionSegment : var=FunctionSegments.initFunctionSegmentRule( functionSegmentRule, value, JavaRules.createDefinitionRule( ));return classMemberRule.set( new OrRule( Lists.of( JavaRules.createWhitespaceRule( ), JavaStructureStatement.createStructureStatementRule( new TypeRule( "definition", JavaRules.createDefinitionRule( )), value), JavaMethod.createMethodRule( functionSegment), JavaEnumValues.createEnumValuesRule( value), JavaRules.createStructureRule( "record", classMemberRule), JavaRules.createStructureRule( "interface", classMemberRule), JavaRules.createStructureRule( "class", classMemberRule), JavaRules.createStructureRule( "enum", classMemberRule))));;}
}
