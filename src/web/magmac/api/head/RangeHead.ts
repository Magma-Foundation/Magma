import { None } from "../../../magmac/api/None";
import { Option } from "../../../magmac/api/Option";
import { Some } from "../../../magmac/api/Some";
export class RangeHead {
	RangeHead(length : int) : public {this.length=length;this.counter=0;;}
	next() : Option<Integer> {if(true){ value : int=this.counter;this.counter++;return new Some<>( value);;}if(true){ return new None<>( );;};}
}
