import { Node } from "../../../magmac/app/compile/node/Node";
export class EmptyAfterAll {
	public afterAll( roots : UnitSet<Node>) : UnitSet<Node> {
		return roots;
	}
}
