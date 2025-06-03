import { CompileResult } from "../../../magmac/app/compile/error/CompileResult";
import { JavaRoot } from "../../../magmac/app/lang/node/JavaRoot";
import { UnitSet } from "../../../magmac/app/stage/unit/UnitSet";
export interface Compiler {
	parseAndGenerate(units : UnitSet<JavaRoot>) : CompileResult<UnitSet<String>>;
}
