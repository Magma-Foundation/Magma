import { None } from "../../../magmac/api/None";
import { Option } from "../../../magmac/api/Option";
import { Some } from "../../../magmac/api/Some";
export class SingleHead<T> {
	SingleHead(element : T) : public {break;break;;}
	next() : Option<T> {if(true){ return new None<>( );;}break;return new Some<>( 0.element);;}
}
