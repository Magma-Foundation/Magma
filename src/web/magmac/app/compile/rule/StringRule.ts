import { Ok } from "../../../../magmac/api/result/Ok";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { CompileResults } from "../../../../magmac/app/compile/error/CompileResults";
import { CompileErrors } from "../../../../magmac/app/compile/error/error/CompileErrors";
import { MapNode } from "../../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../../magmac/app/compile/node/Node";
export class StringRule {
	public static findString( node : Node,  key : String) : CompileResult<String> {return node.findString( key).map( 0).orElseGet( ( )->CompileErrors.createNodeError( "String '" + key + "' not present", node));;}
	public lex( input : String) : CompileResult<Node> {return CompileResults.fromResult( new Ok<>( new MapNode( ).withString( this.key, input)));;}
	public generate( node : Node) : CompileResult<String> {return StringRule.findString( node, this.key);;}
}
