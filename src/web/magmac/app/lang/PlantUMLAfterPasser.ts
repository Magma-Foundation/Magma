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
	createInherits(child : Node, key : String) : CompileResult<NodeList> {return child.findNode( key).map( 0).orElseGet( 0);;}
	getNodeListCompileResult(child : Node, implemented : Node) : CompileResult<NodeList> {return StringRule.findString( child, "name").flatMapValue( 0);;}
	createInherits0(type : Node, child : String) : CompileResult<NodeList> {return PlantUMLAfterPasser.stringifyType( type).mapValue( 0);;}
	stringifyType(type : Node) : CompileResult<String> {if(true){ return StringRule.findString( type, "base");;}if(true){ return StringRule.findString( type, "value");;}return CompileErrors.createNodeError( "Cannot find value for type", type);;}
	replaceRootChild(child : Node) : CompileResult<NodeList> {maybeExtended : var=PlantUMLAfterPasser.createInherits( child, "extended");maybeImplemented : var=PlantUMLAfterPasser.createInherits( child, "implemented");return maybeExtended.flatMapValue( 0);;}
	replaceRootChildren(node : Node) : CompileResult<NodeList> {return PlantUMLAfterPasser.replaceChildrenToList( node).mapValue( 0);;}
	replaceChildrenToList(node : Node) : CompileResult<List<NodeList>> {return node.findNodeList( "children").orElse( InlineNodeList.empty( )).iter( ).map( PlantUMLAfterPasser.replaceRootChild).collect( new CompileResultCollector<>( new ListCollector<>( )));;}
	pass(state : ParseState, node : Node) : ParseResult {if(true){ result : var=PlantUMLAfterPasser.replaceRootChildren( node).flatMapValue( 0);return new InlinePassResult( new Some<>( result));;}if(true){ child : var=node.findNodeList( "segments").orElse( InlineNodeList.empty( )).findLast( ).orElse( null).findString( "value").orElse( "");dependency : var=new MapNode( "dependency").withString( "parent", state.findLocation( ).name( )).withString( "child", child);tuple : ParseUnit<Node>=new ParseUnitImpl<Node>( state, dependency);return new InlinePassResult( new Some<>( CompileResults.Ok( tuple)));;}return InlinePassResult.empty( );;}
	getTuple2CompileResult(state : ParseState, node : Node, values : NodeList) : CompileResult<ParseUnit<Node>> {return CompileResults.Ok( new ParseUnitImpl<Node>( state, node.withNodeList( "children", values)));;}
}
