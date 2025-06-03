import { Iter } from "../../../../../magmac/api/iter/Iter";
import { Iters } from "../../../../../magmac/api/iter/Iters";
import { Pattern } from "../../../../../java/util/regex/Pattern";
export class DelimitedDivider {
	divide(input : String) : Iter<String> {return Iters.fromValues( input.split( Pattern.quote( this.delimiter)));;}
	createDelimiter() : String {return this.delimiter;;}
}
