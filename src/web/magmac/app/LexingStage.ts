import { Result } from "../../magmac/api/result/Result";
import { CompileResult } from "../../magmac/app/compile/error/CompileResult";
import { Node } from "../../magmac/app/compile/node/Node";
import { ApplicationError } from "../../magmac/app/error/ApplicationError";
import { ThrowableError } from "../../magmac/app/error/ThrowableError";
import { Sources } from "../../magmac/app/io/sources/Sources";
import { Lexer } from "../../magmac/app/stage/lexer/Lexer";
import { UnitSet } from "../../magmac/app/stage/unit/UnitSet";
import { Function } from "../../java/util/function/Function";
export class LexingStage<T> {
	getUnitSetApplicationErrorResult(sources1 : Sources) : Result<UnitSet<T>, ApplicationError> {return 0;;}
	getUnitSetApplicationErrorResult(units : UnitSet<String>) : Result<UnitSet<T>, ApplicationError> {return 0;;}
}
