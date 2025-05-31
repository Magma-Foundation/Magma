import { CompileResult } from "../../../magmac/app/compile/error/CompileResult";
export interface Stage<T,  R> {
	apply(initial : T) : CompileResult<R>;
}
