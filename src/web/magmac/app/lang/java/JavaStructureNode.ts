import { Option } from "../../../../magmac/api/Option";
import { List } from "../../../../magmac/api/collect/list/List";
import { StructureValue } from "../../../../magmac/app/lang/node/StructureValue";
export class JavaStructureNode {
	JavaStructureNode(type : JavaLang.JavaStructureType, structureNode : StructureValue<JavaLang.JavaType, JavaStructureMember>, parameters : Option<List<JavaParameter>>, variants : Option<List<JavaLang.JavaType>>) : public {break;break;break;break;;}
	type() : JavaLang.JavaStructureType {return 0.type;;}
	name() : String {return 0.value.name( );;}
	implemented() : Option<List<JavaLang.JavaType>> {return 0.value.maybeImplemented( );;}
	extended() : Option<List<JavaLang.JavaType>> {return 0.value.maybeExtended( );;}
}
