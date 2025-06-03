import { InlineNodeList } from "../../../../magmac/app/compile/node/InlineNodeList";
import { NodeList } from "../../../../magmac/app/compile/node/NodeList";
import { ParseState } from "../../../../magmac/app/stage/parse/ParseState";
import { BiFunction } from "../../../../java/util/function/BiFunction";
export class ParseUnitImpl<T> {
	ParseUnitImpl(state : ParseState, node : T) : public {break;break;;}
	toLocationUnit() : Unit<T> {return 0;;}
	merge(merge : BiFunction<ParseState, T, R>) : R {return 0;;}
	retainWithList() : ParseUnit<NodeList> {return 0;;}
	left() : ParseState {return 0.state;;}
	right() : T {return 0.node;;}
}
