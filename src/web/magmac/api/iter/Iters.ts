import { Option } from "../../../magmac/api/Option";
import { EmptyHead } from "../../../magmac/api/head/EmptyHead";
import { HeadedIter } from "../../../magmac/api/head/HeadedIter";
import { RangeHead } from "../../../magmac/api/head/RangeHead";
import { SingleHead } from "../../../magmac/api/head/SingleHead";
export class Iters {
	fromArray : Iter<T>;
	fromOption(option : Option<T>) : Iter<T>;
	fromValues : Iter<T>;
	empty() : Iter<T>;
}
