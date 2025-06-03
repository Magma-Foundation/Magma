import { CompileResult } from "../../../magmac/app/compile/error/CompileResult";
import { Serializable } from "../../../magmac/app/lang/Serializable";
import { JavaLang } from "../../../magmac/app/lang/java/JavaLang";
import { Generator } from "../../../magmac/app/stage/generate/Generator";
import { Parser } from "../../../magmac/app/stage/parse/Parser";
import { UnitSet } from "../../../magmac/app/stage/unit/UnitSet";
export class StagedCompiler<R extends Serializable> {
	 StagedCompiler( parser : Parser<JavaLang.JavaRoot, R>,  generator : Generator) : public {this.parser=parser;this.generator=generator;;}
	public parseAndGenerate( units : UnitSet<JavaLang.JavaRoot>) : CompileResult<UnitSet<String>> {return this.parser.apply( units).mapValue( 0).flatMapValue( this.generator.apply);;}
}
