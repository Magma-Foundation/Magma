import { Lists } from "../../../../magmac/api/collect/list/Lists";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { Context } from "../../../../magmac/app/compile/error/context/Context";
import { NodeContext } from "../../../../magmac/app/compile/error/context/NodeContext";
import { StringContext } from "../../../../magmac/app/compile/error/context/StringContext";
import { CompileError } from "../../../../magmac/app/compile/error/error/CompileError";
import { CompileErrors } from "../../../../magmac/app/compile/error/error/CompileErrors";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { ImmutableCompileError } from "../../../../magmac/app/error/ImmutableCompileError";
export class TypeRule {
	private createError( context : Context,  err : CompileError) : CompileError {return new ImmutableCompileError( "Cannot use type '" + this.type + "'", context, Lists.of( err));;}
	public lex( input : String) : CompileResult<Node> {return this.childRule.lex( input).mapValue( 0).mapErr( 0);;}
	public generate( node : Node) : CompileResult<String> {if(true){ return this.childRule.generate( node).mapErr( 0);;}return CompileErrors.createNodeError( "Type '" + this.type + "' not present", node);;}
}
