import { Some } from "../../../magmac/api/Some";
import { Tuple2 } from "../../../magmac/api/Tuple2";
import { ListCollector } from "../../../magmac/api/iter/collect/ListCollector";
import { InlineCompileResult } from "../../../magmac/app/compile/error/InlineCompileResult";
import { InlineNodeList } from "../../../magmac/app/compile/node/InlineNodeList";
import { Node } from "../../../magmac/app/compile/node/Node";
import { NodeList } from "../../../magmac/app/compile/node/NodeList";
import { InlinePassResult } from "../../../magmac/app/stage/InlinePassResult";
import { PassResult } from "../../../magmac/app/stage/PassResult";
import { Passer } from "../../../magmac/app/stage/Passer";
import { ParseState } from "../../../magmac/app/stage/parse/ParseState";
export class FlattenJava {
	getChildren(state : ParseState, node : Node) : InlinePassResult {
		 Tuple2<ParseState, Node> parseStateNodeTuple2=new Tuple2<>( state, node);
		return new InlinePassResult( new Some<>( InlineCompileResult.fromOk( parseStateNodeTuple2)));
	}
	pass(state : ParseState, node : Node) : PassResult {
		if(node.is( "root")){ 
		 NodeList values=new InlineNodeList( node.findNodeList( "children").orElse( InlineNodeList.empty( )).iter( ).filter( (child : Node) => !child.is( "package")).collect( new ListCollector<>( )));
		return FlattenJava.getChildren( state, node.withNodeList( "children", values));}
		if(node.is( "record")){ 
		return FlattenJava.getChildren( state, node.retype( "class"));}
		return InlinePassResult.empty( );
	}
}
