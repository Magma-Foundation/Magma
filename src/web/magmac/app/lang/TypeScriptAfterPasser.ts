import { None } from "../../../magmac/api/None";
import { Option } from "../../../magmac/api/Option";
import { Some } from "../../../magmac/api/Some";
import { Tuple2 } from "../../../magmac/api/Tuple2";
import { Lists } from "../../../magmac/api/collect/list/Lists";
import { CompileResult } from "../../../magmac/app/compile/error/CompileResult";
import { InlineCompileResult } from "../../../magmac/app/compile/error/InlineCompileResult";
import { InlineNodeList } from "../../../magmac/app/compile/node/InlineNodeList";
import { MapNode } from "../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../magmac/app/compile/node/Node";
import { NodeList } from "../../../magmac/app/compile/node/NodeList";
import { NodeListCollector } from "../../../magmac/app/compile/node/NodeListCollector";
import { InlinePassResult } from "../../../magmac/app/stage/InlinePassResult";
import { PassResult } from "../../../magmac/app/stage/PassResult";
import { Passer } from "../../../magmac/app/stage/Passer";
import { ParseState } from "../../../magmac/app/stage/parse/ParseState";
export class TypeScriptAfterPasser {
	passImport(state : ParseState, node : Node) : Option<PassResult> {if(!node.is( "import")){ return new None<>( );} int namespaceSize=state.findLocation( ).namespace( ).size( ); NodeList copy=Lists.repeat( "..", namespaceSize).iter( ).map( (String value) ->new MapNode( ).withString( "value", value)).collect( new NodeListCollector( )); NodeList segments=node.findNodeList( "segments").orElse( InlineNodeList.empty( )); Option<CompileResult<Tuple2<ParseState, Node>>> map=segments.findLast( ).map( (Node last) ->{ String value=last.findString( "value").orElse( ""); NodeList values=copy.addAll( segments); Node node1=node.withString( "child", value).withNodeList( "segments", values);return InlineCompileResult.fromOk( new Tuple2<>( state, node1));});return new Some<>( new InlinePassResult( map));}
	passMethod(state : ParseState, node : Node) : Option<PassResult> {if(node.is( "method")){  Node header=node.findNode( "header").orElse( new MapNode( )); NodeList parameters=node.findNodeList( "parameters").orElse( InlineNodeList.empty( )); Node withParameters=header.withNodeList( "parameters", parameters);return new Some<>( new InlinePassResult( new Some<>( InlineCompileResult.fromOk( new Tuple2<>( state, node.withNode( "header", withParameters))))));}return new None<>( );}
	pass(state : ParseState, node : Node) : PassResult {return TypeScriptAfterPasser.passImport( state, node).or( () ->TypeScriptAfterPasser.passMethod( state, node)).orElseGet( () ->InlinePassResult.empty( ));}
}
