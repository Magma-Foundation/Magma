import { InlineNodeList } from "../../../../magmac/app/compile/node/InlineNodeList";
import { NodeList } from "../../../../magmac/app/compile/node/NodeList";
import { ParseState } from "../../../../magmac/app/stage/parse/ParseState";
import { BiFunction } from "../../../../java/util/function/BiFunction";
export class ParseUnitImpl<T> {private final state : ParseState;private final node : T;
	 ParseUnitImpl( state : ParseState,  node : T) : public {
		this.state=state;
		this.node=node;
	}
	public toLocationUnit() : Unit<T> {
		return this.merge( ( state, node)->new SimpleUnit<>( state.findLocation( ), node));
	}
	public merge( merge : BiFunction<ParseState, T, R>) : R {
		return merge.apply( this.state, this.node);
	}
	public retainWithList() : ParseUnit<NodeList> {
		return new ParseUnitImpl<>( this.state, InlineNodeList.empty( ));
	}
	public left() : ParseState {
		return this.state;
	}
	public right() : T {
		return this.node;
	}
}
