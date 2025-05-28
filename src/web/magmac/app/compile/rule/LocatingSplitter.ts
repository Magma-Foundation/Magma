import { Option } from "../../../../magmac/api/Option";
import { Tuple2 } from "../../../../magmac/api/Tuple2";
import { Locator } from "../../../../magmac/app/compile/rule/locate/Locator";
export class LocatingSplitter {
	split(input : String) : Option<Tuple2<String, String>> {
		return this.locator.locate( input, this.infix).map( (Integer separator)  => { String left=input.substring( 0, separator); String right=input.substring( separator+this.infix.length( ));return new Tuple2<>( left, right);});
	}
	createMessage() : String {
		return "Infix '" + this.infix + "' not present";
	}
	merge(leftString : String, rightString : String) : String {
		return leftString+this.infix+rightString;
	}
}
