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
	fromResult(result : Result<T, CompileError>) : CompileResult<T> {return new InlineCompileResult<T>( 0);;}
	Ok(value : T) : CompileResult<T> {return new InlineCompileResult<T>( new Ok<T, CompileError>( 0));;}
	NodeErr(message : String, context : Node) : CompileResult<T> {return new InlineCompileResult<>( new Err<>( new ImmutableCompileError( 0, new NodeContext( 0))));;}
	fromErrWithString(message : String, context : String) : CompileResult<T> {return 0.fromWithContext( 0, new StringContext( 0));;}
	fromWithContext(message : String, context : Context) : CompileResult<T> {return new InlineCompileResult<>( new Err<>( new ImmutableCompileError( 0, 0)));;}
}
