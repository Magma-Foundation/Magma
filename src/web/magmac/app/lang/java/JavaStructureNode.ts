import { Option } from "../../../../magmac/api/Option";
import { List } from "../../../../magmac/api/collect/list/List";
import { JavaStructureType } from "../../../../magmac/app/lang/node/JavaStructureType";
import { JavaType } from "../../../../magmac/app/lang/node/JavaType";
import { StructureValue } from "../../../../magmac/app/lang/node/StructureValue";
export class JavaStructureNode {
	JavaStructureNode(type : JavaStructureType, structureNode : StructureValue<JavaType, JavaStructureMember>, parameters : Option<List<JavaParameter>>, variants : Option<List<JavaType>>) : public {break;break;break;break;;}
	type() : JavaStructureType {break;;}
	name() : String {break;;}
	implemented() : Option<List<JavaType>> {break;;}
	extended() : Option<List<JavaType>> {break;;}
}
