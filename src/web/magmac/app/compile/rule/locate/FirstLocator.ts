import { None } from "../../../../../magmac/api/None";
import { Option } from "../../../../../magmac/api/Option";
import { Some } from "../../../../../magmac/api/Some";
export class FirstLocator {
	public locate( input : String,  infix : String) : Option<Integer> { let index : var=input.indexOf( infix);if(true){ return new None<Integer>( );;}return new Some<>( index);;}
}
