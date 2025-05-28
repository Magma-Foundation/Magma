import { Lists } from "../../../../magmac/api/collect/list/Lists";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { InlineCompileResult } from "../../../../magmac/app/compile/error/InlineCompileResult";
import { Context } from "../../../../magmac/app/compile/error/context/Context";
import { NodeContext } from "../../../../magmac/app/compile/error/context/NodeContext";
import { StringContext } from "../../../../magmac/app/compile/error/context/StringContext";
import { CompileError } from "../../../../magmac/app/compile/error/error/CompileError";
import { CompileErrors } from "../../../../magmac/app/compile/error/error/CompileErrors";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { ImmutableCompileError } from "../../../../magmac/app/error/ImmutableCompileError";
export class TypeRule {
	private createError( context() : Context,  err() : CompileError) : CompileError {
		return new ImmutableCompileError( "Cannot use type '" + this.type + "'", context, Lists.of( err));
	}
	public lex( input() : String) : CompileResult<Node> {
		return InlineCompileResult.fromResult( this.childRule.lex( input).result( ).mapValue( ( node() : Node) => node.retype( this.type)).mapErr( ( err() : CompileError) => this.createError( new StringContext( input), err)));
	}
	public generate( node() : Node) : CompileResult<String> {
		if(node.is( this.type)){ 
		return InlineCompileResult.fromResult( this.childRule.generate( node).result( ).mapErr( ( err() : CompileError) => this.createError( new NodeContext( node), err)));}
		return CompileErrors.createNodeError( "Type '" + this.type + "' not present", node);
	}
}
