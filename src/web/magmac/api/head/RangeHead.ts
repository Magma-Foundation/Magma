import { None } from "../../../magmac/api/None";
import { Option } from "../../../magmac/api/Option";
import { Some } from "../../../magmac/api/Some";
export class RangeHead {private final length() : int;private counter() : int;
	 RangeHead( length() : int) : public {
		this.length=length;
		this.counter=0;
	}
	public next() : Option<Integer> {
		if(this.counter<this.length){ 
		 value() : int=this.counter;
		this.counter++;
		return new Some<>( value);}
		else{ 
		return new None<>( );}
	}
}
