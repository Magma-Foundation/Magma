import { NodeList } from "../../../../magmac/app/compile/node/NodeList";
import { ParseState } from "../../../../magmac/app/stage/parse/ParseState";
import { BiFunction } from "../../../../java/util/function/BiFunction";
export interface ParseUnit {
	 toLocationUnit() : Unit<T>;
	 merge( merge : BiFunction<ParseState, T, R>) : R;
	 retainWithList() : ParseUnit<NodeList>;
	 left() : ParseState;
	 right() : T;
}
