import { CompileResult } from "../../../magmac/app/compile/error/CompileResult";
import { Location } from "../../../magmac/app/io/Location";
import { Roots } from "../../../magmac/app/stage/Roots";
import { Generator } from "../../../magmac/app/stage/generate/Generator";
import { Lexer } from "../../../magmac/app/stage/lexer/Lexer";
import { Parser } from "../../../magmac/app/stage/parse/Parser";
import { Map } from "../../../magmac/api/collect/map/Map";
export class StagedCompiler {
	public compile( units : Map<Location, String>) : CompileResult<Map<Location, String>> {
		return this.lexer.apply( units).flatMapValue( ( trees : Roots) => this.parser.apply( trees)).flatMapValue( ( trees : Roots) => this.generator.apply( trees));
	}
}
