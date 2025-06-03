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
	deserialize(node : Node) : CompileResult<JavaStructureMember> {return 0.orError( 0, 0, 0.of( 0.wrap( 0.deserializeWhitespace), 0.wrap( 0.deserialize), 0.wrap( 0.deserialize), 0.wrap( 0.deserialize), 0.wrap( new JavaLang.JavaStructureNodeDeserializer( 0.JavaStructureType.Class)), 0.wrap( new JavaLang.JavaStructureNodeDeserializer( 0.JavaStructureType.Interface)), 0.wrap( new JavaLang.JavaStructureNodeDeserializer( 0.JavaStructureType.Record)), 0.wrap( new JavaLang.JavaStructureNodeDeserializer( 0.JavaStructureType.Enum))));;}
	createClassMemberRule() : Rule {break;break;break;break;break;return 0.set( new OrRule( 0.of( 0.createWhitespaceRule( ), 0.createStructureStatementRule( new TypeRule( 0, 0.createDefinitionRule( )), 0), 0.createMethodRule( 0), 0.createEnumValuesRule( 0), 0.createStructureRule( 0, 0), 0.createStructureRule( 0, 0), 0.createStructureRule( 0, 0), 0.createStructureRule( 0, 0))));;}
}
