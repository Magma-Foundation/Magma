import { Option } from "../../magmac/api/Option";
import { Some } from "../../magmac/api/Some";
import { Error } from "../../magmac/api/error/Error";
import { Compiler } from "../../magmac/app/compile/Compiler";
import { ApplicationError } from "../../magmac/app/error/ApplicationError";
import { ThrowableError } from "../../magmac/app/error/ThrowableError";
import { Targets } from "../../magmac/app/io/targets/Targets";
import { Serializable } from "../../magmac/app/lang/Serializable";
import { JavaLang } from "../../magmac/app/lang/java/JavaLang";
import { UnitSet } from "../../magmac/app/stage/unit/UnitSet";
export class CompileApplication<R extends Serializable> {
	CompileApplication(compiler : Compiler, targets : Targets) : public {break;break;;}
	parseAndStore(units : UnitSet<JavaLang.JavaRoot>) : Option<Error> {return this.compiler.parseAndGenerate( units).toResult( ).mapErr( ApplicationError.new).match( 0, Some.new);;}
}
