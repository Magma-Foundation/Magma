import { Lists } from "../../../../magmac/api/collect/list/Lists";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { NodeContext } from "../../../../magmac/app/compile/error/context/NodeContext";
import { CompileError } from "../../../../magmac/app/compile/error/error/CompileError";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { StringContext } from "../../../../magmac/app/compile/error/context/StringContext";
import { ImmutableCompileError } from "../../../../magmac/app/error/ImmutableCompileError";
export class ContextRule {
	public lex( input : String) : CompileResult<Node> {
		return this.rule.lex( input).mapErr( ( err : CompileError) => new ImmutableCompileError( this.message, new StringContext( input), Lists.of( err)));
	}
	public generate( node : Node) : CompileResult<String> {
		return this.rule.generate( node).mapErr( ( err : CompileError) => new ImmutableCompileError( this.message, new NodeContext( node), Lists.of( err)));
	}
}
