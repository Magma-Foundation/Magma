import { CompileResult } from "../../../magmac/app/compile/error/CompileResult";
export interface Stage {
	 apply( initial() : T) : CompileResult<R>;
}
