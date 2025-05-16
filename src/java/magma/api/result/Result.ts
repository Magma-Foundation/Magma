import { Function } from "./java/util/function.ts";
interface Result<T, X> {
	match<R>(whenOk: (arg0 : T) => R, whenErr: (arg0 : X) => R): R;
}
