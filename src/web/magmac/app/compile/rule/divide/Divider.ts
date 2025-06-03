import { Iter } from "../../../../../magmac/api/iter/Iter";
export interface Divider {
	 divide( input : String) : Iter<String>;
	 createDelimiter() : String;
}
