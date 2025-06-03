import { CompileResult } from "../../../magmac/app/compile/error/CompileResult";
import { Serializable } from "../../../magmac/app/lang/Serializable";
import { JavaRoot } from "../../../magmac/app/lang/node/JavaRoot";
import { Generator } from "../../../magmac/app/stage/generate/Generator";
import { Parser } from "../../../magmac/app/stage/parse/Parser";
import { UnitSet } from "../../../magmac/app/stage/unit/UnitSet";
export class StagedCompiler<R extends Serializable> {
	StagedCompiler(parser : Parser<JavaRoot, R>, generator : Generator) : public {break;break;;}
	parseAndGenerate(units : UnitSet<JavaRoot>) : CompileResult<UnitSet<String>> {break;;}
}
