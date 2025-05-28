import { Tuple2 } from "../../../../magmac/api/Tuple2";
import { Result } from "../../../../magmac/api/result/Result";
import { CompileError } from "../../../../magmac/app/compile/error/error/CompileError";
import { BiFunction } from "../../../../java/util/function/BiFunction";
import { Function } from "../../../../java/util/function/Function";
import { Supplier } from "../../../../java/util/function/Supplier";
export class InlineCompileResult {
	temp : ?;
	InlineCompileResult : private{
	}
	fromResult : CompileResult<T>{
	}
	mapValue : CompileResult<R>{
	}
	match : R{
	}
	mapErr : CompileResult<T>{
	}
	flatMapValue : CompileResult<R>{
	}
	and : CompileResult<Tuple2<T, R>>{
	}
	result : Result<T, CompileError>{
	}
	merge : CompileResult<T>{
	}
}
