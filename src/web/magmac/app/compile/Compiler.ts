import { CompileResult } from "../../../magmac/app/compile/error/CompileResult";
import { JavaLang } from "../../../magmac/app/lang/java/JavaLang";
import { UnitSet } from "../../../magmac/app/stage/unit/UnitSet";
export interface Compiler {
	 parseAndGenerate( units : UnitSet<JavaLang.JavaRoot>) : CompileResult<UnitSet<String>>;
}
