import { None } from "../../../../../magmac/api/None";
import { Option } from "../../../../../magmac/api/Option";
import { Some } from "../../../../../magmac/api/Some";
export class LastLocator {
	locate(input : String, infix : String) : Option<Integer> {
		 int index=input.lastIndexOf( infix);
		if(-1==index){ 
		return new None<Integer>( );}
		return new Some<>( index);
	}
}
