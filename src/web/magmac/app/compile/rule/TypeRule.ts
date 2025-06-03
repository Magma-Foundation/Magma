import { Lists } from "../../../../magmac/api/collect/list/Lists";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { CompileResults } from "../../../../magmac/app/compile/error/CompileResults";
import { Context } from "../../../../magmac/app/compile/error/context/Context";
import { NodeContext } from "../../../../magmac/app/compile/error/context/NodeContext";
import { StringContext } from "../../../../magmac/app/compile/error/context/StringContext";
import { CompileError } from "../../../../magmac/app/compile/error/error/CompileError";
import { CompileErrors } from "../../../../magmac/app/compile/error/error/CompileErrors";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { ImmutableCompileError } from "../../../../magmac/app/error/ImmutableCompileError";
export class TypeRule {
	createError(context : Context, err : CompileError) : CompileError {return new ImmutableCompileError( "Cannot use type '" + this.type + "'", context, Lists.of( err));;}
	lex(input : String) : CompileResult<Node> {return CompileResults.fromResult( this.childRule.lex( input).toResult( ).mapValue( 0).mapErr( 0));;}
	generate(node : Node) : CompileResult<String> {if(true){ return CompileResults.fromResult( this.childRule.generate( node).toResult( ).mapErr( 0));;}return CompileErrors.createNodeError( "Type '" + this.type + "' not present", node);;}
}
