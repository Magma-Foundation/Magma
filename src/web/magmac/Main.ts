export class Main {
	public static main() : void { let sources : Sources=new PathSources( Paths.get( ".", "src", "java"));Main.loadSources( sources).match( Main.getNext, Some.new).ifPresent( 0);;}
	private static getNext( result : UnitSet<JavaLang.Root>) : Option<Error> {return Iters.fromValues( new PlantUMLTargetPlatform( ), new TypeScriptTargetPlatform( )).map( 0).flatMap( Iters.fromOption).next( );;}
	private static loadSources( sources : Sources) : Result<UnitSet<JavaLang.Root>, ApplicationError> { let lexer : Lexer=new RuleLexer( JavaRules.createRule( ));return new LexingStage<JavaLang.Root>( lexer, 0).getUnitSetApplicationErrorResult( sources);;}
	private static run( roots : UnitSet<JavaLang.Root>,  platform : TargetPlatform) : Option<Error> {return platform.createApplication( ).parseAndStore( roots);;}
}
