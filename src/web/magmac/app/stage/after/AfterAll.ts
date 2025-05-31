import { Node } from "../../../../magmac/app/compile/node/Node";
import { UnitSet } from "../../../../magmac/app/stage/unit/UnitSet";
export interface AfterAll {
	afterAll(roots : UnitSet<Node>) : UnitSet<Node>;
}
