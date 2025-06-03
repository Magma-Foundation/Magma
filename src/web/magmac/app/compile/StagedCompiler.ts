export class StagedCompiler<R extends Serializable> {
	 StagedCompiler( parser : Parser<JavaLang.Root, R>,  generator : Generator) : public {this.parser=parser;this.generator=generator;;}
	public parseAndGenerate( units : UnitSet<JavaLang.Root>) : CompileResult<UnitSet<String>> {return this.parser.apply( units).mapValue( 0).flatMapValue( this.generator.apply);;}
}
