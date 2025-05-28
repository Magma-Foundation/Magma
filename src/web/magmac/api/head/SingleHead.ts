import { None } from "../../../magmac/api/None";
import { Option } from "../../../magmac/api/Option";
import { Some } from "../../../magmac/api/Some";
export class SingleHead {private final element : T;private retrieved : boolean;
	 SingleHead( element : T) : public {
		this.element=element;
		this.retrieved=false;
	}
	public next() : Option<T> {
		if(this.retrieved){ 
		return new None<>( );}
		this.retrieved=true;
		return new Some<>( this.element);
	}
}
