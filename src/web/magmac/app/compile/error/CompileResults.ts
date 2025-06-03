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
	fromResult(result : Result<T, CompileError>) : CompileResult<T> {;;}
	Ok(value : T) : CompileResult<T> {;;}
	NodeErr(message : String, context : Node) : CompileResult<T> {;;}
	fromErrWithString(message : String, context : String) : CompileResult<T> {;;}
	fromWithContext(message : String, context : Context) : CompileResult<T> {;;}
}
