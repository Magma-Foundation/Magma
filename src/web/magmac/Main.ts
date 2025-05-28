import { Console } from "../jvm/io/Console";
import { Option } from "../magmac/api/Option";
import { Iters } from "../magmac/api/iter/Iters";
import { ApplicationBuilder } from "../magmac/app/ApplicationBuilder";
import { Error } from "../magmac/api/error/Error";
import { PlantUMLTargetPlatform } from "../magmac/app/config/PlantUMLTargetPlatform";
import { TargetPlatform } from "../magmac/app/config/TargetPlatform";
import { TypeScriptTargetPlatform } from "../magmac/app/config/TypeScriptTargetPlatform";
import { PathSources } from "../magmac/app/io/sources/PathSources";
import { Sources } from "../magmac/app/io/sources/Sources";
import { Paths } from "../java/nio/file/Paths";
export class Main {
	main() : void {
		sources : Sources=new PathSources( Paths.get( ".", "src", "java"));
		Iters.fromValues( new PlantUMLTargetPlatform( ), new TypeScriptTargetPlatform( )).map( (platform : TargetPlatform) => ApplicationBuilder.run( platform, sources)).flatMap( (option : Option<Error>) => Iters.fromOption( option)).next( ).ifPresent( (error : Error) => Console.handleError( error.display( )));
	}
}
