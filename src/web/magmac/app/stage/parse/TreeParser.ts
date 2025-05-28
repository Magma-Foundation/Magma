import { Tuple2 } from "../../../../magmac/api/Tuple2";
import { Map } from "../../../../magmac/api/collect/map/Map";
import { MapCollector } from "../../../../magmac/api/collect/map/MapCollector";
import { Ok } from "../../../../magmac/api/result/Ok";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { CompileResultCollector } from "../../../../magmac/app/compile/error/CompileResultCollector";
import { InlineCompileResult } from "../../../../magmac/app/compile/error/InlineCompileResult";
import { CompileError } from "../../../../magmac/app/compile/error/error/CompileError";
import { InlineNodeList } from "../../../../magmac/app/compile/node/InlineNodeList";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { NodeList } from "../../../../magmac/app/compile/node/NodeList";
import { Location } from "../../../../magmac/app/io/Location";
import { AfterAll } from "../../../../magmac/app/stage/AfterAll";
import { MapRoots } from "../../../../magmac/app/stage/MapRoots";
import { Passer } from "../../../../magmac/app/stage/Passer";
import { Roots } from "../../../../magmac/app/stage/Roots";
export class TreeParser {
	temp : ?;
	temp : ?;
	temp : ?;
	TreeParser(beforeChild : Passer, afterChild : Passer, afterAllChildren : AfterAll) : public {
		this.beforeChild=beforeChild;
		this.afterChild=afterChild;
		this.afterAllChildren=afterAllChildren;
	}
	parseNodeLists(state : ParseState, root : Node) : CompileResult<Tuple2<ParseState, Node>> {
		 Tuple2<ParseState, Node> parseStateNodeTuple2=new Tuple2<>( state, root);
		return root.iterNodeLists( ).fold( InlineCompileResult.fromOk( parseStateNodeTuple2), (current : CompileResult<Tuple2<ParseState, Node>>, entry : Tuple2<String, NodeList>) => current.flatMapValue( (inner : Tuple2<ParseState, Node>) => this.parseNodeList( inner, entry)));
	}
	parseNodeList(current : Tuple2<ParseState, Node>, entry : Tuple2<String, NodeList>) : CompileResult<Tuple2<ParseState, Node>> {
		 ParseState currentState=current.left( );
		 Node currentNode=current.right( );
		 String key=entry.left( );
		 CompileResult<Tuple2<ParseState, NodeList>> initial=InlineCompileResult.fromOk( new Tuple2<>( currentState, InlineNodeList.empty( )));
		return entry.right( ).iter( ).fold( initial, (currentTupleResult : CompileResult<Tuple2<ParseState, NodeList>>, node : Node) => currentTupleResult.flatMapValue( (currentTuple : Tuple2<ParseState, NodeList>) => { ParseState currentState1=currentTuple.left( ); NodeList currentElements=currentTuple.right( );return this.parseTree( currentState1, node).mapValue( (parsed : Tuple2<ParseState, Node>) => { ParseState newState=parsed.left( ); Node newElement=parsed.right( );return new Tuple2<>( newState, currentElements.add( newElement));});})).mapValue( (newTuple : Tuple2<ParseState, NodeList>) => new Tuple2<>( newTuple.left( ), currentNode.withNodeList( key, newTuple.right( ))));
	}
	parse(location : Location, root : Node) : CompileResult<Tuple2<Location, Node>> {
		 ParseState initial=new ImmutableParseState( location);
		return this.parseTree( initial, root).mapValue( (parsed : Tuple2<ParseState, Node>) => new Tuple2<>( parsed.left( ).findLocation( ), parsed.right( )));
	}
	parseTree(state : ParseState, root : Node) : CompileResult<Tuple2<ParseState, Node>> {
		return this.beforeChild.pass( state, root).orElseGet( ( )->new Tuple2<>( state, root)).flatMapValue( (beforeTuple : Tuple2<ParseState, Node>) => this.parseNodeLists( beforeTuple.left( ), beforeTuple.right( )).flatMapValue( (nodeListsTuple : Tuple2<ParseState, Node>) => { ParseState state1=nodeListsTuple.left( ); Node node=nodeListsTuple.right( );return this.afterChild.pass( state1, node).orElseGet( ( )->new Tuple2<>( state1, node));}));
	}
	apply(initial : Roots) : CompileResult<Roots> {
		return initial.iter( ).map( (tuple : Tuple2<Location, Node>) => this.parse( tuple.left( ), tuple.right( ))).collect( new CompileResultCollector<>( new MapCollector<>( ))).flatMapValue( (parsed : Map<Location, Node>) => InlineCompileResult.fromResult( new Ok<Roots, CompileError>( new MapRoots( this.afterAllChildren.afterAll( parsed)))));
	}
}
