import { Err } from "../../../../magmac/api/result/Err";
import { Ok } from "../../../../magmac/api/result/Ok";
import { Result } from "../../../../magmac/api/result/Result";
import { Context } from "../../../../magmac/app/compile/error/context/Context";
import { NodeContext } from "../../../../magmac/app/compile/error/context/NodeContext";
import { StringContext } from "../../../../magmac/app/compile/error/context/StringContext";
import { CompileError } from "../../../../magmac/app/compile/error/error/CompileError";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { ImmutableCompileError } from "../../../../magmac/app/error/ImmutableCompileError";
export class CompileResults {
	public static fromResult( result : Result<T, CompileError>) : CompileResult<T> {return new InlineCompileResult<T>( result);;}
	public static Ok( value : T) : CompileResult<T> {return new InlineCompileResult<T>( new Ok<T, CompileError>( value));;}
	public static NodeErr( message : String,  context : Node) : CompileResult<T> {return new InlineCompileResult<>( new Err<>( new ImmutableCompileError( message, new NodeContext( context))));;}
	public static fromErrWithString( message : String,  context : String) : CompileResult<T> {return CompileResults.fromWithContext( message, new StringContext( context));;}
	private static fromWithContext( message : String,  context : Context) : CompileResult<T> {return new InlineCompileResult<>( new Err<>( new ImmutableCompileError( message, context)));;}
}
