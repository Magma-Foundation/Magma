import { InlineNodeList } from "../../../../magmac/app/compile/node/InlineNodeList";
import { NodeList } from "../../../../magmac/app/compile/node/NodeList";
import { ParseState } from "../../../../magmac/app/stage/parse/ParseState";
import { BiFunction } from "../../../../java/util/function/BiFunction";
export class ParseUnitImpl<T> {
	ParseUnitImpl(state : ParseState, node : T) : public {break;break;;}
	toLocationUnit() : Unit<T> {break;;}
	merge(merge : BiFunction<ParseState, T, R>) : R {break;;}
	retainWithList() : ParseUnit<NodeList> {break;;}
	left() : ParseState {break;;}
	right() : T {break;;}
}
