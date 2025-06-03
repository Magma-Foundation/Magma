export class CompileApplication<R extends Serializable> {
	 CompileApplication( compiler : Compiler,  targets : Targets) : public {this.targets=targets;this.compiler=compiler;;}
	public parseAndStore( units : UnitSet<JavaLang.Root>) : Option<Error> {return this.compiler.parseAndGenerate( units).toResult( ).mapErr( ApplicationError.new).match( 0, Some.new);;}
}
