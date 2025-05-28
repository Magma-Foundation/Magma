import { Option } from "../../../magmac/api/Option";
import { Some } from "../../../magmac/api/Some";
import { Tuple2 } from "../../../magmac/api/Tuple2";
import { Iter } from "../../../magmac/api/iter/Iter";
import { Iters } from "../../../magmac/api/iter/Iters";
import { ListCollector } from "../../../magmac/api/iter/collect/ListCollector";
import { InlineCompileResult } from "../../../magmac/app/compile/error/InlineCompileResult";
import { InlineNodeList } from "../../../magmac/app/compile/node/InlineNodeList";
import { MapNode } from "../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../magmac/app/compile/node/Node";
import { NodeList } from "../../../magmac/app/compile/node/NodeList";
import { InlinePassResult } from "../../../magmac/app/stage/InlinePassResult";
import { PassResult } from "../../../magmac/app/stage/PassResult";
import { Passer } from "../../../magmac/app/stage/Passer";
import { ParseState } from "../../../magmac/app/stage/parse/ParseState";
export class PlantUMLAfterPasser {
	createInherits(child : Node, key : String) : Option<Node> {
		return child.findNode( key).map( (implemented : Node) => new MapNode( "inherits").withString( "child", child.findString( "name").orElse( "")).withString( "parent", PlantUMLAfterPasser.findValue( implemented)));
	}
	findValue(type : Node) : String {
		if(type.is( "template")){ 
		return type.findString( "base").orElse( "?");}
		if(type.is( "symbol")){ 
		return type.findString( "value").orElse( "?");}
		return "?";
	}
	replaceRootChild(child : Node) : Iter<Node> {
		 Option<Node> extended=PlantUMLAfterPasser.createInherits( child, "extended");
		 Option<Node> implemented=PlantUMLAfterPasser.createInherits( child, "implemented");
		return Iters.fromValues( child).concat( Iters.fromOption( extended)).concat( Iters.fromOption( implemented));
	}
	replaceRootChildren(node : Node) : NodeList {
		return new InlineNodeList( node.findNodeList( "children").orElse( InlineNodeList.empty( )).iter( ).flatMap( (child : Node) => PlantUMLAfterPasser.replaceRootChild( child)).collect( new ListCollector<>( )));
	}
	pass(state : ParseState, node : Node) : PassResult {
		if(node.is( "root")){ 
		 NodeList values=PlantUMLAfterPasser.replaceRootChildren( node);
		 Tuple2<ParseState, Node> tuple=new Tuple2<>( state, node.withNodeList( "children", values));
		return new InlinePassResult( new Some<>( InlineCompileResult.fromOk( tuple)));}
		if(node.is( "import")){ 
		 String child=node.findNodeList( "segments").orElse( InlineNodeList.empty( )).findLast( ).orElse( null).findString( "value").orElse( "");
		 Node dependency=new MapNode( "dependency").withString( "parent", state.findLocation( ).name( )).withString( "child", child);
		 Tuple2<ParseState, Node> tuple=new Tuple2<>( state, dependency);
		return new InlinePassResult( new Some<>( InlineCompileResult.fromOk( tuple)));}
		return InlinePassResult.empty( );
	}
}
