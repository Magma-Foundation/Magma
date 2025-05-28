import { Some } from "../../../magmac/api/Some";
import { List } from "../../../magmac/api/collect/list/List";
import { ListCollector } from "../../../magmac/api/iter/collect/ListCollector";
import { CompileResult } from "../../../magmac/app/compile/error/CompileResult";
import { CompileResultCollector } from "../../../magmac/app/compile/error/CompileResultCollector";
import { CompileResults } from "../../../magmac/app/compile/error/CompileResults";
import { CompileErrors } from "../../../magmac/app/compile/error/error/CompileErrors";
import { InlineNodeList } from "../../../magmac/app/compile/node/InlineNodeList";
import { MapNode } from "../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../magmac/app/compile/node/Node";
import { NodeList } from "../../../magmac/app/compile/node/NodeList";
import { NodeListCollector } from "../../../magmac/app/compile/node/NodeListCollector";
import { StringRule } from "../../../magmac/app/compile/rule/StringRule";
import { InlinePassResult } from "../../../magmac/app/stage/result/InlinePassResult";
import { ParseResult } from "../../../magmac/app/stage/result/ParseResult";
import { ParseUnit } from "../../../magmac/app/stage/unit/ParseUnit";
import { ParseUnitImpl } from "../../../magmac/app/stage/unit/ParseUnitImpl";
import { Passer } from "../../../magmac/app/stage/Passer";
import { ParseState } from "../../../magmac/app/stage/parse/ParseState";
export class PlantUMLAfterPasser {
	private static createInherits( child : Node,  key : String) : CompileResult<NodeList> {
		return child.findNode( key).map( ( implemented : Node) => PlantUMLAfterPasser.getNodeListCompileResult( child, implemented)).orElseGet( ( )->CompileResults.fromOk( InlineNodeList.empty( )));
	}
	private static getNodeListCompileResult( child : Node,  implemented : Node) : CompileResult<NodeList> {
		return StringRule.findString( child, "name").flatMapValue( ( name : String) => PlantUMLAfterPasser.createInherits0( implemented, name));
	}
	private static createInherits0( type : Node,  child : String) : CompileResult<NodeList> {
		return PlantUMLAfterPasser.stringifyType( type).mapValue( ( parent : String) => {
		 node : Node=new MapNode( "inherits").withString( "child", child).withString( "parent", parent);
		return InlineNodeList.of( node);});
	}
	private static stringifyType( type : Node) : CompileResult<String> {
		if(type.is( "template")){ 
		return StringRule.findString( type, "base");}
		if(type.is( "symbol-type")){ 
		return StringRule.findString( type, "value");}
		return CompileErrors.createNodeError( "Cannot find value for type", type);
	}
	private static replaceRootChild( child : Node) : CompileResult<NodeList> {
		 maybeExtended : CompileResult<NodeList>=PlantUMLAfterPasser.createInherits( child, "extended");
		 maybeImplemented : CompileResult<NodeList>=PlantUMLAfterPasser.createInherits( child, "implemented");
		return maybeExtended.flatMapValue( ( extendedResult : NodeList) => maybeImplemented.mapValue( ( implementedResult : NodeList) => InlineNodeList.of( child).addAll( extendedResult).addAll( implementedResult)));
	}
	private static replaceRootChildren( node : Node) : CompileResult<NodeList> {
		return PlantUMLAfterPasser.replaceChildrenToList( node).mapValue( ( lists : List<NodeList>) => lists.iter( ).flatMap( ( list : NodeList) => list.iter( )).collect( new NodeListCollector( )));
	}
	private static replaceChildrenToList( node : Node) : CompileResult<List<NodeList>> {
		return node.findNodeList( "children").orElse( InlineNodeList.empty( )).iter( ).map( ( child : Node) => PlantUMLAfterPasser.replaceRootChild( child)).collect( new CompileResultCollector<>( new ListCollector<>( )));
	}
	public pass( state : ParseState,  node : Node) : ParseResult {
		if(node.is( "root")){ 
		 result : CompileResult<ParseUnit<Node>>=PlantUMLAfterPasser.replaceRootChildren( node).flatMapValue( ( values : NodeList) => PlantUMLAfterPasser.getTuple2CompileResult( state, node, values));
		return new InlinePassResult( new Some<>( result));}
		if(node.is( "import")){ 
		 child : String=node.findNodeList( "segments").orElse( InlineNodeList.empty( )).findLast( ).orElse( null).findString( "value").orElse( "");
		 dependency : Node=new MapNode( "dependency").withString( "parent", state.findLocation( ).name( )).withString( "child", child);
		 tuple : ParseUnit<Node>=new ParseUnitImpl<Node>( state, dependency);
		return new InlinePassResult( new Some<>( CompileResults.fromOk( tuple)));}
		return InlinePassResult.empty( );
	}
	private static getTuple2CompileResult( state : ParseState,  node : Node,  values : NodeList) : CompileResult<ParseUnit<Node>> {
		return CompileResults.fromOk( new ParseUnitImpl<Node>( state, node.withNodeList( "children", values)));
	}
}
