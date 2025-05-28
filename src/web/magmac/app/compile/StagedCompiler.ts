import { CompileResult } from "../../../magmac/app/compile/error/CompileResult";
import { Node } from "../../../magmac/app/compile/node/Node";
import { UnitSet } from "../../../magmac/app/stage/UnitSet";
import { Generator } from "../../../magmac/app/stage/generate/Generator";
import { Lexer } from "../../../magmac/app/stage/lexer/Lexer";
import { Parser } from "../../../magmac/app/stage/parse/Parser";
export class StagedCompiler {
	public compile( units : UnitSet<String>) : CompileResult<UnitSet<String>> {
		return this.lexer.apply( units).flatMapValue( ( trees : UnitSet<Node>) => this.parser.apply( trees)).flatMapValue( ( trees : UnitSet<Node>) => this.generator.apply( trees));
	}
}
