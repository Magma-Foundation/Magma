import { Option } from "../../../magmac/api/Option";
import { EmptyHead } from "../../../magmac/api/head/EmptyHead";
import { HeadedIter } from "../../../magmac/api/head/HeadedIter";
import { RangeHead } from "../../../magmac/api/head/RangeHead";
import { SingleHead } from "../../../magmac/api/head/SingleHead";
export class Iters {
	fromArray(array : T[]) : Iter<T> {break;;}
	fromOption(option : Option<T>) : Iter<T> {break;;}
	fromValues(...values : T[]) : Iter<T> {break;;}
	empty() : Iter<T> {break;;}
}
