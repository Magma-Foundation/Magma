export class PlantUMLTargetPlatform {
	public createTargetPath() : Path {return Paths.get( ".", "diagrams");;}
	public createExtension() : String {return "puml";;}
	public createRule() : Rule {return PlantUMLRoot.createRule( );;}
	public createCompiler() : Compiler { let generator : Generator=new RuleGenerator( this.createRule( ));return new StagedCompiler<PlantUMLRoot>( new JavaPlantUMLParser( ), generator);;}
	private createApplication0( targets : Targets) : Application {return new CompileApplication<>( createCompiler( ), targets);;}
	public createApplication() : Application { let targetPath : var=this.createTargetPath( ); let extension : var=this.createExtension( ); let targets : Targets=new PathTargets( targetPath, extension);return this.createApplication0( targets);;}
}
