import { Tuple2 } from "../../../../magmac/api/Tuple2";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { CompileResultCollector } from "../../../../magmac/app/compile/error/CompileResultCollector";
import { CompileResults } from "../../../../magmac/app/compile/error/CompileResults";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { NodeList } from "../../../../magmac/app/compile/node/NodeList";
import { Location } from "../../../../magmac/app/io/Location";
import { UnitSetCollector } from "../../../../magmac/app/io/sources/UnitSetCollector";
import { AfterAll } from "../../../../magmac/app/stage/after/AfterAll";
import { ParseUnit } from "../../../../magmac/app/stage/unit/ParseUnit";
import { ParseUnitImpl } from "../../../../magmac/app/stage/unit/ParseUnitImpl";
import { Passer } from "../../../../magmac/app/stage/Passer";
import { Unit } from "../../../../magmac/app/stage/unit/Unit";
import { UnitSet } from "../../../../magmac/app/stage/unit/UnitSet";
export class TreeParser {
	TreeParser(beforeChild : Passer, afterChild : Passer, afterAllChildren : AfterAll) : public;
	parseNodeListEntry(current : ParseUnit<Node>, entry : Tuple2<String, NodeList>) : CompileResult<ParseUnit<Node>>;
	getParseUnitCompileResult(node : Node, currentState1 : ParseState, currentElements : NodeList) : CompileResult<ParseUnit<NodeList>>;
	parseTree(state : ParseState, root : Node) : CompileResult<ParseUnit<Node>>;
	parseNodes(withNodeLists : ParseUnit<Node>) : CompileResult<ParseUnit<Node>>;
	parseNodeEntry(current : ParseUnit<Node>, entry : Tuple2<String, Node>) : CompileResult<ParseUnit<Node>>;
	parseNodeLists(beforeTuple : ParseUnit<Node>) : CompileResult<ParseUnit<Node>>;
	apply(initial : UnitSet<Node>) : CompileResult<UnitSet<Node>>;
	parseUnit(unit : Unit<Node>) : CompileResult<Unit<Node>>;
}
