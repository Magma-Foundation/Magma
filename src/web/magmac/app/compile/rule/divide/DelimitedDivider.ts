import { Iter } from "../../../../../magmac/api/iter/Iter";
import { Iters } from "../../../../../magmac/api/iter/Iters";
import { Pattern } from "../../../../../java/util/regex/Pattern";
export class DelimitedDivider {
	divide(input : String) : Iter<String> {return 0.fromValues( 0.split( 0.quote( 0.delimiter)));;}
	createDelimiter() : String {return 0.delimiter;;}
}
