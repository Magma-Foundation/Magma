import { Err } from "../../../../../magmac/api/result/Err";
import { CompileResult } from "../../../../../magmac/app/compile/error/CompileResult";
import { CompileResults } from "../../../../../magmac/app/compile/error/CompileResults";
import { NodeContext } from "../../../../../magmac/app/compile/error/context/NodeContext";
import { StringContext } from "../../../../../magmac/app/compile/error/context/StringContext";
import { Node } from "../../../../../magmac/app/compile/node/Node";
import { ImmutableCompileError } from "../../../../../magmac/app/error/ImmutableCompileError";
export class CompileErrors {
	createNodeError(message : String, node : Node) : CompileResult<T> {return 0.fromResult( new Err<>( new ImmutableCompileError( 0, new NodeContext( 0))));;}
	createStringError(message : String, context : String) : CompileResult<T> {return 0.fromResult( new Err<>( new ImmutableCompileError( 0, new StringContext( 0))));;}
}
