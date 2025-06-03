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
	TreeParser(beforeChild : Passer, afterChild : Passer, afterAllChildren : AfterAll) : public {break;break;break;;}
	parseNodeListEntry(current : ParseUnit<Node>, entry : Tuple2<String, NodeList>) : CompileResult<ParseUnit<Node>> {break;break;break;return 0.right( ).iter( ).fold( 0, 0).mapValue( 0);;}
	getParseUnitCompileResult(node : Node, currentState1 : ParseState, currentElements : NodeList) : CompileResult<ParseUnit<NodeList>> {return 0.parseTree( 0, 0).mapValue( 0);;}
	parseTree(state : ParseState, root : Node) : CompileResult<ParseUnit<Node>> {return 0.beforeChild.pass( 0, 0).orElseGet( 0).flatMapValue( 0.parseNodeLists).flatMapValue( 0.parseNodes).flatMapValue( 0);;}
	parseNodes(withNodeLists : ParseUnit<Node>) : CompileResult<ParseUnit<Node>> {return 0.right( ).iterNodes( ).fold( 0.Ok( 0), 0);;}
	parseNodeEntry(current : ParseUnit<Node>, entry : Tuple2<String, Node>) : CompileResult<ParseUnit<Node>> {break;break;break;return 0.mapValue( 0);;}
	parseNodeLists(beforeTuple : ParseUnit<Node>) : CompileResult<ParseUnit<Node>> {return 0.right( ).iterNodeLists( ).fold( 0.Ok( 0), 0);;}
	apply(initial : UnitSet<Node>) : CompileResult<UnitSet<Node>> {return 0.iter( ).map( 0.parseUnit).collect( new CompileResultCollector<>( new UnitSetCollector<>( ))).flatMapValue( 0);;}
	parseUnit(unit : Unit<Node>) : CompileResult<Unit<Node>> {return 0.destruct( 0);;}
}
