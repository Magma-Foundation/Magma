import { CompileResult } from "../../../magmac/app/compile/error/CompileResult";
import { JavaRootSegment } from "../../../magmac/app/lang/node/JavaRootSegment";
import { Root } from "../../../magmac/app/lang/node/Root";
import { UnitSet } from "../../../magmac/app/stage/unit/UnitSet";
export interface Compiler {
	parseAndGenerate(units : UnitSet<Root<JavaRootSegment>>) : CompileResult<UnitSet<String>>;
}
