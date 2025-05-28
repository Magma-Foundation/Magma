import { None } from "../../../magmac/api/None";
import { Option } from "../../../magmac/api/Option";
import { Some } from "../../../magmac/api/Some";
import { Lists } from "../../../magmac/api/collect/list/Lists";
import { CompileResult } from "../../../magmac/app/compile/error/CompileResult";
import { CompileResults } from "../../../magmac/app/compile/error/CompileResults";
import { InlineNodeList } from "../../../magmac/app/compile/node/InlineNodeList";
import { MapNode } from "../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../magmac/app/compile/node/Node";
import { NodeList } from "../../../magmac/app/compile/node/NodeList";
import { NodeListCollector } from "../../../magmac/app/compile/node/NodeListCollector";
import { Passer } from "../../../magmac/app/stage/Passer";
import { ParseState } from "../../../magmac/app/stage/parse/ParseState";
import { InlinePassResult } from "../../../magmac/app/stage/result/InlinePassResult";
import { ParseResult } from "../../../magmac/app/stage/result/ParseResult";
import { ParseUnit } from "../../../magmac/app/stage/unit/ParseUnit";
import { ParseUnitImpl } from "../../../magmac/app/stage/unit/ParseUnitImpl";
export class TypeScriptAfterPasser {
	private static passImport( state : ParseState,  node : Node) : Option<ParseResult> {
		if(!node.is( "import")){ 
		return new None<>( );}
		 namespaceSize : int=state.findLocation( ).namespace( ).size( );
		 copy : NodeList=Lists.repeat( "..", namespaceSize).iter( ).map( ( value : String) => new MapNode( ).withString( "value", value)).collect( new NodeListCollector( ));
		 segments : NodeList=node.findNodeList( "segments").orElse( InlineNodeList.empty( ));
		 map : Option<CompileResult<ParseUnit<Node>>>=segments.findLast( ).map( ( last : Node) => {
		 value : String=last.findString( "value").orElse( "");
		 values : NodeList=copy.addAll( segments);
		 node1 : Node=node.withString( "child", value).withNodeList( "segments", values);
		return CompileResults.fromOk( new ParseUnitImpl<Node>( state, node1));});
		return new Some<>( new InlinePassResult( map));
	}
	private static passMethod( state : ParseState,  node : Node) : Option<ParseResult> {
		if(node.is( "method")){ 
		 header : Node=node.findNode( "header").orElse( new MapNode( ));
		 parameters : NodeList=node.findNodeList( "parameters").orElse( InlineNodeList.empty( ));
		 withParameters : Node=header.withNodeList( "parameters", parameters);
		return new Some<>( InlinePassResult.from( state, node.withNode( "header", withParameters).withString( "after-children", "\n\t")));}
		return new None<>( );
	}
	private static passVariadic( state : ParseState,  node : Node) : Option<ParseResult> {
		if(node.is( "variadic")){ 
		return new Some<>( InlinePassResult.from( state, node.retype( "array")));}
		else{ 
		return new None<>( );}
	}
	private static format( state : ParseState,  node : Node) : Option<ParseResult> {
		if(node.is( "statement")||node.is( "block")){ 
		 before : Node=node.withString( "before", "\n\t\t");
		return new Some<>( InlinePassResult.from( state, before));}
		return new None<>( );
	}
	public pass( state : ParseState,  node : Node) : ParseResult {
		return TypeScriptAfterPasser.passImport( state, node).or( ( )->TypeScriptAfterPasser.passMethod( state, node)).or( ( )->TypeScriptAfterPasser.format( state, node)).or( ( )->TypeScriptAfterPasser.passVariadic( state, node)).or( ( )->this.passTemp( state, node)).orElseGet( ( )->InlinePassResult.empty( ));
	}
	private passTemp( state : ParseState,  node : Node) : Option<ParseResult> {
		if(node.is( "statement")){ 
		 value1 : var=node.findNodeOrError( "value").flatMapValue( ( value : Node) => this.getObjectCompileResult( state, value));
		return new Some<>( new InlinePassResult( new Some<>( value1)));}
		return new None<>( );
	}
	private getObjectCompileResult( state : ParseState,  value : Node) : CompileResult<ParseUnit<Node>> {
		if(value.is( "assignment")){ 
		return value.findNodeOrError( "destination").mapValue( ( destination : Node) => this.getNode( destination)).mapValue( result->new ParseUnitImpl<>( state, result));}
		return CompileResults.fromOk( new ParseUnitImpl<>( state, value));
	}
	private getNode( destination : Node) : Node {
		if(destination.is( "definition")){ 
		 modifiers : NodeList=destination.findNodeList( "modifiers").orElse( InlineNodeList.empty( ));
		 newModifiers : NodeList=modifiers.add( new MapNode( ).withString( "value", "let"));
		return destination.withNodeList( "modifiers", newModifiers);}
		else{ 
		return destination;}
	}
}
