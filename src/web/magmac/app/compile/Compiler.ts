import { CompileResult } from "../../../magmac/app/compile/error/CompileResult";
import { UnitSet } from "../../../magmac/app/stage/unit/UnitSet";
export interface Compiler {
	 compile( units : UnitSet<String>) : CompileResult<UnitSet<String>>;
}
