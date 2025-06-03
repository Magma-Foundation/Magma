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
	createInherits(child : Node, key : String) : CompileResult<NodeList> {return 0.findNode( 0).map( 0).orElseGet( 0);;}
	getNodeListCompileResult(child : Node, implemented : Node) : CompileResult<NodeList> {return 0.findString( 0, 0).flatMapValue( 0);;}
	createInherits0(type : Node, child : String) : CompileResult<NodeList> {return 0.stringifyType( 0).mapValue( 0);;}
	stringifyType(type : Node) : CompileResult<String> {if(true){ return 0.findString( 0, 0);;}if(true){ return 0.findString( 0, 0);;}return 0.createNodeError( 0, 0);;}
	replaceRootChild(child : Node) : CompileResult<NodeList> {break;break;return 0.flatMapValue( 0);;}
	replaceRootChildren(node : Node) : CompileResult<NodeList> {return 0.replaceChildrenToList( 0).mapValue( 0);;}
	replaceChildrenToList(node : Node) : CompileResult<List<NodeList>> {return 0.findNodeList( 0).orElse( 0.empty( )).iter( ).map( 0.replaceRootChild).collect( new CompileResultCollector<>( new ListCollector<>( )));;}
	pass(state : ParseState, node : Node) : ParseResult {if(true){ break;return new InlinePassResult( new Some<>( 0));;}if(true){ break;break;break;return new InlinePassResult( new Some<>( 0.Ok( 0)));;}return 0.empty( );;}
	getTuple2CompileResult(state : ParseState, node : Node, values : NodeList) : CompileResult<ParseUnit<Node>> {return 0.Ok( new ParseUnitImpl<Node>( 0, 0.withNodeList( 0, 0)));;}
}
