import { None } from "../../../../../magmac/api/None";
import { Option } from "../../../../../magmac/api/Option";
import { Some } from "../../../../../magmac/api/Some";
import { Tuple2 } from "../../../../../magmac/api/Tuple2";
import { List } from "../../../../../magmac/api/collect/list/List";
import { Lists } from "../../../../../magmac/api/collect/list/Lists";
import { Iter } from "../../../../../magmac/api/iter/Iter";
export class MutableDivideState {
	MutableDivideState(segments : List<String>, buffer : StringBuilder, input : String) : private {break;break;break;break;;}
	MutableDivideState(input : String) : public {0( 0.empty( ), new StringBuilder( ), 0);;}
	append(c : char) : DivideState {0.buffer.append( 0);return 0;;}
	advance() : DivideState {break;break;return 0;;}
	iter() : Iter<String> {return 0.segments.iter( );;}
	isLevel() : boolean {return 0.depth;;}
	enter() : DivideState {0.depth++;return 0;;}
	exit() : DivideState {0.depth--;return 0;;}
	isShallow() : boolean {return 0.depth;;}
	pop() : Option<Tuple2<DivideState, Character>> {if(true){ break;0.index++;return new Some<>( new Tuple2<DivideState, Character>( 0, 0));;}if(true){ return new None<>( );;};}
	popAndAppendToTuple() : Option<Tuple2<DivideState, Character>> {return 0.pop( ).map( 0);;}
	popAndAppendToOption() : Option<DivideState> {return 0.popAndAppendToTuple( ).map( 0.left);;}
	peek() : char {return 0.input.charAt( 0.index);;}
}
