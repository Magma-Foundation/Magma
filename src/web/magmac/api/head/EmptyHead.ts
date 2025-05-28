import { None } from "../../../magmac/api/None";
import { Option } from "../../../magmac/api/Option";
export class EmptyHead {
	next() : Option<T> {return new None<>( );}
}
