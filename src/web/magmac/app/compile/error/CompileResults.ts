import { Ok } from "../../../../magmac/api/result/Ok";
import { Result } from "../../../../magmac/api/result/Result";
import { CompileError } from "../../../../magmac/app/compile/error/error/CompileError";
export class CompileResults {
	public static fromResult( result : Result<T, CompileError>) : CompileResult<T> {
		return new InlineCompileResult<T>( result);
	}
	public static fromOk( value : T) : CompileResult<T> {
		return CompileResults.fromResult( new Ok<>( value));
	}
}
