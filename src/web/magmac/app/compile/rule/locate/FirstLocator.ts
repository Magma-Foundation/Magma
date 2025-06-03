import { None } from "../../../../../magmac/api/None";
import { Option } from "../../../../../magmac/api/Option";
import { Some } from "../../../../../magmac/api/Some";
export class FirstLocator {
	locate(input : String, infix : String) : Option<Integer> {index : int=input.indexOf( infix);if(true){ return new None<Integer>( );;}return new Some<>( index);;}
}
