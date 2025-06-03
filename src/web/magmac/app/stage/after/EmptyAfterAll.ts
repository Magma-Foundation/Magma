import { Node } from "../../../../magmac/app/compile/node/Node";
import { UnitSet } from "../../../../magmac/app/stage/unit/UnitSet";
export class EmptyAfterAll {
	afterAll(roots : UnitSet<Node>) : UnitSet<Node> {return 0;;}
}
