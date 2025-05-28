import { None } from "../../../../magmac/api/None";
import { Option } from "../../../../magmac/api/Option";
import { Some } from "../../../../magmac/api/Some";
export class Joiner {
	Joiner() : public {
		this( "");
	}
	createInitial() : Option<String> {
		return new None<>( );
	}
	fold(current : Option<String>, element : String) : Option<String> {
		return new Some<>( current.map( (inner : String) => inner+this.delimiter+element).orElse( element));
	}
}
