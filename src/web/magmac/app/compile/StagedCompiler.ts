import { CompileResult } from "../../../magmac/app/compile/error/CompileResult";
import { Location } from "../../../magmac/app/io/Location";
import { Generator } from "../../../magmac/app/stage/generate/Generator";
import { Lexer } from "../../../magmac/app/stage/lexer/Lexer";
import { Parser } from "../../../magmac/app/stage/parse/Parser";
import { Map } from "../../../magmac/api/collect/map/Map";
export class StagedCompiler {
	compile : CompileResult<Map<Location, String>>{
	}
}
