import { Console } from "../jvm/io/Console";
import { Option } from "../magmac/api/Option";
import { Some } from "../magmac/api/Some";
import { Error } from "../magmac/api/error/Error";
import { Iters } from "../magmac/api/iter/Iters";
import { Result } from "../magmac/api/result/Result";
import { LexingStage } from "../magmac/app/LexingStage";
import { PlantUMLTargetPlatform } from "../magmac/app/config/PlantUMLTargetPlatform";
import { TargetPlatform } from "../magmac/app/config/TargetPlatform";
import { TypeScriptTargetPlatform } from "../magmac/app/config/TypeScriptTargetPlatform";
import { ApplicationError } from "../magmac/app/error/ApplicationError";
import { PathSources } from "../magmac/app/io/sources/PathSources";
import { Sources } from "../magmac/app/io/sources/Sources";
import { JavaDeserializers } from "../magmac/app/lang/java/JavaDeserializers";
import { JavaRules } from "../magmac/app/lang/JavaRules";
import { JavaLang } from "../magmac/app/lang/java/JavaLang";
import { Lexer } from "../magmac/app/stage/lexer/Lexer";
import { RuleLexer } from "../magmac/app/stage/lexer/RuleLexer";
import { UnitSet } from "../magmac/app/stage/unit/UnitSet";
import { Paths } from "../java/nio/file/Paths";
export class Main {
	public static main() : void { let sources : Sources=new PathSources( Paths.get( ".", "src", "java"));Main.loadSources( sources).match( Main.getNext, Some.new).ifPresent( 0);;}
	private static getNext( result : UnitSet<JavaLang.Root>) : Option<Error> {return Iters.fromValues( new PlantUMLTargetPlatform( ), new TypeScriptTargetPlatform( )).map( 0).flatMap( Iters.fromOption).next( );;}
	private static loadSources( sources : Sources) : Result<UnitSet<JavaLang.Root>, ApplicationError> { let lexer : Lexer=new RuleLexer( JavaRules.createRule( ));return new LexingStage<JavaLang.Root>( lexer, 0).getUnitSetApplicationErrorResult( sources);;}
	private static run( roots : UnitSet<JavaLang.Root>,  platform : TargetPlatform) : Option<Error> {return platform.createApplication( ).parseAndStore( roots);;}
}
