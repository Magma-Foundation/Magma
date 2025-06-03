export class TypeScriptTargetPlatform {
	public createTargetPath() : Path {return Paths.get( ".", "src", "web");;}
	public createExtension() : String {return "ts";;}
	public createRule() : Rule {return TypescriptRules.createRule( );;}
	public createCompiler() : Compiler { let parser : Parser<JavaLang.Root, TypescriptLang.TypescriptRoot>=new JavaTypescriptParser( ); let generator : Generator=new RuleGenerator( this.createRule( ));return new StagedCompiler<TypescriptLang.TypescriptRoot>( parser, generator);;}
	public createApplication() : Application { let targetPath : var=this.createTargetPath( ); let extension : var=this.createExtension( ); let targets : Targets=new PathTargets( targetPath, extension);return new CompileApplication<>( this.createCompiler( ), targets);;}
}
