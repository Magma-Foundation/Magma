import { Option } from "../../../../magmac/api/Option";
import { List } from "../../../../magmac/api/collect/list/List";
import { MapNode } from "../../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../../magmac/app/compile/node/Node";
export class JavaStructureNode {
	JavaStructureNode : public;
	serialize : Node;
	type : JavaStructureType;
	name : String;
	implemented : Option<List<Type>>;
	extended : Option<List<Type>>;
}
