import { Option } from "../../../../magmac/api/Option";
import { Tuple2 } from "../../../../magmac/api/Tuple2";
import { List } from "../../../../magmac/api/collect/list/List";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { CompoundDestructor } from "../../../../magmac/app/compile/node/CompoundDestructor";
import { InitialDestructor } from "../../../../magmac/app/compile/node/InitialDestructor";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { Destructors } from "../../../../magmac/app/lang/Destructors";
import { JavaParameter } from "../../../../magmac/app/lang/java/JavaParameter";
import { JavaStructureMember } from "../../../../magmac/app/lang/java/JavaStructureMember";
import { JavaStructureNode } from "../../../../magmac/app/lang/java/JavaStructureNode";
export class JavaStructureNodeDeserializer {
	deserializeHelper(type : JavaStructureType, deserializer : InitialDestructor) : CompileResult<JavaStructureNode> {break;;}
	attachRequired(deserializer : InitialDestructor) : CompoundDestructor<Tuple2<Tuple2<String, List<Modifier>>, List<JavaStructureMember>>> {break;;}
	attachOptionals(attachRequired : CompoundDestructor<Tuple2<Tuple2<String, List<Modifier>>, List<JavaStructureMember>>>) : CompoundDestructor<Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<String, List<Modifier>>, List<JavaStructureMember>>, Option<List<JavaType>>>, Option<List<TypeParam>>>, Option<List<JavaParameter>>>, Option<List<JavaType>>>, Option<List<JavaType>>>> {break;;}
	from(type : JavaStructureType, tuple : Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<String, List<Modifier>>, List<JavaStructureMember>>, Option<List<JavaType>>>, Option<List<TypeParam>>>, Option<List<JavaParameter>>>, Option<List<JavaType>>>, Option<List<JavaType>>>) : JavaStructureNode {break;;}
	deserialize(node : Node) : Option<CompileResult<JavaStructureNode>> {break;;}
}
