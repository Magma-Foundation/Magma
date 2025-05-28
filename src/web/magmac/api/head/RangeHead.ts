import { None } from "../../../magmac/api/None";
import { Option } from "../../../magmac/api/Option";
import { Some } from "../../../magmac/api/Some";
export class RangeHead {
	temp : ?;
	temp : ?;
	RangeHead(length : int) : public {this.length=length;this.counter=0;}
	next() : Option<Integer> {if(this.counter<this.length){  int value=this.counter;this.counter++;return new Some<>( value);}else{ return new None<>( );}}
}
