import { Option } from "../../../../magmac/api/Option";
import { Tuple2 } from "../../../../magmac/api/Tuple2";
import { List } from "../../../../magmac/api/collect/list/List";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { CompoundDestructor } from "../../../../magmac/app/compile/node/CompoundDestructor";
import { InitialDestructor } from "../../../../magmac/app/compile/node/InitialDestructor";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { Destructors } from "../../../../magmac/app/lang/Destructors";
export class JavaStructureNodeDeserializer {
	deserializeHelper(type : JavaStructureType, deserializer : InitialDestructor) : CompileResult<JavaStructureNode> {;;}
	attachRequired(deserializer : InitialDestructor) : CompoundDestructor<Tuple2<Tuple2<String, List<Modifier>>, List<JavaStructureMember>>> {;;}
	attachOptionals(attachRequired : CompoundDestructor<Tuple2<Tuple2<String, List<Modifier>>, List<JavaStructureMember>>>) : CompoundDestructor<Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<String, List<Modifier>>, List<JavaStructureMember>>, Option<List<JavaType>>>, Option<List<TypeParam>>>, Option<List<JavaParameter>>>, Option<List<JavaType>>>, Option<List<JavaType>>>> {;;}
	from(type : JavaStructureType, tuple : Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<String, List<Modifier>>, List<JavaStructureMember>>, Option<List<JavaType>>>, Option<List<TypeParam>>>, Option<List<JavaParameter>>>, Option<List<JavaType>>>, Option<List<JavaType>>>) : JavaStructureNode {;;}
	deserialize(node : Node) : Option<CompileResult<JavaStructureNode>> {;;}
}
