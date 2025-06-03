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
	parseNodeListEntry(current : ParseUnit<Node>, entry : Tuple2<String, NodeList>) : CompileResult<ParseUnit<Node>> {break;break;break;return entry.right( ).iter( ).fold( initial, 0).mapValue( 0);;}
	getParseUnitCompileResult(node : Node, currentState1 : ParseState, currentElements : NodeList) : CompileResult<ParseUnit<NodeList>> {return this.parseTree( currentState1, node).mapValue( 0);;}
	parseTree(state : ParseState, root : Node) : CompileResult<ParseUnit<Node>> {return this.beforeChild.pass( state, root).orElseGet( 0).flatMapValue( this.parseNodeLists).flatMapValue( this.parseNodes).flatMapValue( 0);;}
	parseNodes(withNodeLists : ParseUnit<Node>) : CompileResult<ParseUnit<Node>> {return withNodeLists.right( ).iterNodes( ).fold( CompileResults.Ok( withNodeLists), 0);;}
	parseNodeEntry(current : ParseUnit<Node>, entry : Tuple2<String, Node>) : CompileResult<ParseUnit<Node>> {break;break;break;return parsed.mapValue( 0);;}
	parseNodeLists(beforeTuple : ParseUnit<Node>) : CompileResult<ParseUnit<Node>> {return beforeTuple.right( ).iterNodeLists( ).fold( CompileResults.Ok( beforeTuple), 0);;}
	apply(initial : UnitSet<Node>) : CompileResult<UnitSet<Node>> {return initial.iter( ).map( this.parseUnit).collect( new CompileResultCollector<>( new UnitSetCollector<>( ))).flatMapValue( 0);;}
	parseUnit(unit : Unit<Node>) : CompileResult<Unit<Node>> {return unit.destruct( 0);;}
}
