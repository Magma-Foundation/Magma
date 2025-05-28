import { Some } from "../../magmac/api/Some";
import { Error } from "../../magmac/api/error/Error";
import { Compiler } from "../../magmac/app/compile/Compiler";
import { CompileError } from "../../magmac/app/compile/error/error/CompileError";
import { ApplicationError } from "../../magmac/app/error/ApplicationError";
import { ThrowableError } from "../../magmac/app/error/ThrowableError";
import { Location } from "../../magmac/app/io/Location";
import { Sources } from "../../magmac/app/io/sources/Sources";
import { Targets } from "../../magmac/app/io/targets/Targets";
import { Map } from "../../magmac/api/collect/map/Map";
import { Option } from "../../magmac/api/Option";
import { IOException } from "../../java/io/IOException";
export class CompileApplication {
	temp : ?;
	temp : ?;
	temp : ?;
	CompileApplication(sources : Sources, compiler : Compiler, targets : Targets) : public {
		this.sources=sources;
		this.targets=targets;
		this.compiler=compiler;
	}
	run() : Option<Error> {
		return this.sources.readAll( ).mapErr( (IOException throwable)  => new ThrowableError( throwable)).mapErr( (ThrowableError error)  => new ApplicationError( error)).match( (Map<Location, String> units)  => this.compileAndWrite( units),  (ApplicationError value)  => new Some<>( value));
	}
	compileAndWrite(units : Map<Location, String>) : Option<Error> {
		return this.compiler.compile(units).result()
                .mapErr((CompileError error1)  =>  new ApplicationError(error1))
                .match((Map<Location, String> outputs)  =>  this.targets.writeAll(outputs).map((IOException throwable)  =>  new ThrowableError(throwable)).map((ThrowableError error)  => new ApplicationError( error)), (ApplicationError err)  => new Some<>( err));
	}
}
