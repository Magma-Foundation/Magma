import { Err } from "../../../../../magmac/api/result/Err";
import { CompileResult } from "../../../../../magmac/app/compile/error/CompileResult";
import { InlineCompileResult } from "../../../../../magmac/app/compile/error/InlineCompileResult";
import { NodeContext } from "../../../../../magmac/app/compile/error/context/NodeContext";
import { StringContext } from "../../../../../magmac/app/compile/error/context/StringContext";
import { Node } from "../../../../../magmac/app/compile/node/Node";
import { ImmutableCompileError } from "../../../../../magmac/app/error/ImmutableCompileError";
export class CompileErrors {
	createNodeError(message : String, node : Node) : CompileResult<T>;
	createStringError(message : String, context : String) : CompileResult<T>;
}
