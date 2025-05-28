import { Some } from "../../magmac/api/Some";
import { Error } from "../../magmac/api/error/Error";
import { Compiler } from "../../magmac/app/compile/Compiler";
import { CompileError } from "../../magmac/app/compile/error/error/CompileError";
import { ApplicationError } from "../../magmac/app/error/ApplicationError";
import { ThrowableError } from "../../magmac/app/error/ThrowableError";
import { Sources } from "../../magmac/app/io/sources/Sources";
import { Targets } from "../../magmac/app/io/targets/Targets";
import { Option } from "../../magmac/api/Option";
import { UnitSet } from "../../magmac/app/stage/UnitSet";
import { IOException } from "../../java/io/IOException";
export class CompileApplication {private final sources : Sources;private final targets : Targets;private final compiler : Compiler;
	 CompileApplication( sources : Sources,  compiler : Compiler,  targets : Targets) : public {
		this.sources=sources;
		this.targets=targets;
		this.compiler=compiler;
	}
	public run() : Option<Error> {
		return this.sources.readAll( ).mapErr( ( throwable : IOException) => new ThrowableError( throwable)).mapErr( ( error : ThrowableError) => new ApplicationError( error)).match( ( units : UnitSet<String>) => this.compileAndWrite( units), ( value : ApplicationError) => new Some<>( value));
	}
	private compileAndWrite( units : UnitSet<String>) : Option<Error> {
		return this.compiler.compile( units).result( ).mapErr( ( error1 : CompileError) => new ApplicationError( error1)).match( ( outputs : UnitSet<String>) => this.targets.writeAll( outputs).map( ( throwable : IOException) => new ThrowableError( throwable)).map( ( error : ThrowableError) => new ApplicationError( error)), ( err : ApplicationError) => new Some<>( err));
	}
}
