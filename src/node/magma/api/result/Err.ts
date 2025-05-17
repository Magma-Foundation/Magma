import { Result } from "../../../magma/api/result/Result";
import { Option } from "../../../magma/api/option/Option";
import { Some } from "../../../magma/api/option/Some";
import { None } from "../../../magma/api/option/None";
export class Err<T, X> implements Result<T, X> {
	error: X;
	constructor (error: X) {
		this.error = error;
	}
	findError(): Option<X> {
		return new Some<X>(this/*auto*/.error);
	}
	findValue(): Option<T> {
		return new None<T>(/*auto*/);
	}
	match<R>(whenOk: (arg0 : T) => R, whenErr: (arg0 : X) => R): R {
		return whenErr/*(arg0 : X) => R*/(this/*auto*/.error);
	}
}
