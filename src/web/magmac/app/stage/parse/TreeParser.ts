import { Tuple2 } from "../../../../magmac/api/Tuple2";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { CompileResultCollector } from "../../../../magmac/app/compile/error/CompileResultCollector";
import { CompileResults } from "../../../../magmac/app/compile/error/CompileResults";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { NodeList } from "../../../../magmac/app/compile/node/NodeList";
import { Location } from "../../../../magmac/app/io/Location";
import { UnitSetCollector } from "../../../../magmac/app/io/sources/UnitSetCollector";
import { AfterAll } from "../../../../magmac/app/stage/AfterAll";
import { ParseUnit } from "../../../../magmac/app/stage/ParseUnit";
import { ParseUnitImpl } from "../../../../magmac/app/stage/ParseUnitImpl";
import { Passer } from "../../../../magmac/app/stage/Passer";
import { Unit } from "../../../../magmac/app/stage/Unit";
import { UnitSet } from "../../../../magmac/app/stage/UnitSet";
export class TreeParser {private final beforeChild : Passer;private final afterChild : Passer;private final afterAllChildren : AfterAll;
	 TreeParser( beforeChild : Passer,  afterChild : Passer,  afterAllChildren : AfterAll) : public {
		this.beforeChild=beforeChild;
		this.afterChild=afterChild;
		this.afterAllChildren=afterAllChildren;
	}
	private parseNodeListEntry( current : ParseUnit<Node>,  entry : Tuple2<String, NodeList>) : CompileResult<ParseUnit<Node>> {
		 currentNode : Node=current.right( );
		 key : String=entry.left( );
		 initial : CompileResult<ParseUnit<NodeList>>=CompileResults.fromOk( current.retainWithList( ));
		return entry.right( ).iter( ).fold( initial, ( currentTupleResult : CompileResult<ParseUnit<NodeList>>,  node : Node) => currentTupleResult.flatMapValue( ( currentTuple : ParseUnit<NodeList>) => currentTuple.merge( ( currentState2, currentElements1)->this.getParseUnitCompileResult( node, currentState2, currentElements1)))).mapValue( ( newTuple : ParseUnit<NodeList>) => newTuple.merge( ( state : ParseState,  nodeList : NodeList) => new ParseUnitImpl<>( state, currentNode.withNodeList( key, nodeList))));
	}
	private getParseUnitCompileResult( node : Node,  currentState1 : ParseState,  currentElements : NodeList) : CompileResult<ParseUnit<NodeList>> {
		return this.parseTree( currentState1, node).mapValue( ( parsed : ParseUnit<Node>) => parsed.merge( ( state, node1)->new ParseUnitImpl<>( state, currentElements.add( node1))));
	}
	private parseTree( state : ParseState,  root : Node) : CompileResult<ParseUnit<Node>> {
		return this.beforeChild.pass( state, root).orElseGet( ( )->new ParseUnitImpl<Node>( state, root)).flatMapValue( ( beforeTuple : ParseUnit<Node>) => this.parseNodeLists( beforeTuple)).flatMapValue( withNodeLists->this.parseNodes( withNodeLists)).flatMapValue( ( nodeListsTuple : ParseUnit<Node>) => {
		 state1 : ParseState=nodeListsTuple.left( );
		 node : Node=nodeListsTuple.right( );
		return this.afterChild.pass( state1, node).orElseGet( ( )->new ParseUnitImpl<Node>( state1, node));});
	}
	private parseNodes( withNodeLists : ParseUnit<Node>) : CompileResult<ParseUnit<Node>> {
		return withNodeLists.right( ).iterNodes( ).fold( CompileResults.fromOk( withNodeLists), ( current, entry)->current.flatMapValue( ( inner : ParseUnit<Node>) => this.parseNodeEntry( inner, entry)));
	}
	private parseNodeEntry( current : ParseUnit<Node>,  entry : Tuple2<String, Node>) : CompileResult<ParseUnit<Node>> {
		 currentNode : Node=current.right( );
		 key : String=entry.left( );
		 parsed : CompileResult<ParseUnit<Node>>=this.parseTree( current.left( ), entry.right( ));
		return parsed.mapValue( value->new ParseUnitImpl<>( value.left( ), currentNode.withNode( key, value.right( ))));
	}
	private parseNodeLists( beforeTuple : ParseUnit<Node>) : CompileResult<ParseUnit<Node>> {
		return beforeTuple.right( ).iterNodeLists( ).fold( CompileResults.fromOk( beforeTuple), ( current : CompileResult<ParseUnit<Node>>,  entry : Tuple2<String, NodeList>) => current.flatMapValue( ( inner : ParseUnit<Node>) => this.parseNodeListEntry( inner, entry)));
	}
	public apply( initial : UnitSet<Node>) : CompileResult<UnitSet<Node>> {
		return initial.iter( ).map( ( unit : Unit<Node>) => this.parseUnit( unit)).collect( new CompileResultCollector<>( new UnitSetCollector<>( ))).flatMapValue( ( parsed : UnitSet<Node>) => CompileResults.fromOk( this.afterAllChildren.afterAll( parsed)));
	}
	private parseUnit( unit : Unit<Node>) : CompileResult<Unit<Node>> {
		return unit.deconstruct( ( location : Location,  root : Node) => {
		 initial : ParseState=new ImmutableParseState( location);
		return this.parseTree( initial, root).mapValue( ( parsed : ParseUnit<Node>) => parsed.toLocationUnit( ));});
	}
}
