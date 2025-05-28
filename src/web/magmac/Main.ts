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
		 Sources sources=new PathSources( Paths.get( ".", "src", "java"));
		Iters.fromValues( new PlantUMLTargetPlatform( ), new TypeScriptTargetPlatform( )).map( (TargetPlatform platform)  => ApplicationBuilder.run( platform, sources)).flatMap( (Option<Error> option)  => Iters.fromOption( option)).next( ).ifPresent( (Error error)  => Main.handleError( error));
	}
	handleError(error : Error) : void {
		System.err.println( error.display( ));
	}
}
