import { None } from "../../../../../magmac/api/None";
import { Option } from "../../../../../magmac/api/Option";
import { Some } from "../../../../../magmac/api/Some";
import { Tuple2 } from "../../../../../magmac/api/Tuple2";
import { List } from "../../../../../magmac/api/collect/list/List";
import { Lists } from "../../../../../magmac/api/collect/list/Lists";
import { Iter } from "../../../../../magmac/api/iter/Iter";
export class MutableDivideState {
	MutableDivideState(segments : List<String>, buffer : StringBuilder, input : String) : private {break;break;break;break;;}
	MutableDivideState(input : String) : public {this( Lists.empty( ), new StringBuilder( ), input);;}
	append(c : char) : DivideState {this.buffer.append( c);return this;;}
	advance() : DivideState {break;break;return this;;}
	iter() : Iter<String> {return this.segments.iter( );;}
	isLevel() : boolean {return 0.depth;;}
	enter() : DivideState {this.depth++;return this;;}
	exit() : DivideState {this.depth--;return this;;}
	isShallow() : boolean {return 0.depth;;}
	pop() : Option<Tuple2<DivideState, Character>> {if(true){ break;this.index++;return new Some<>( new Tuple2<DivideState, Character>( this, c));;}if(true){ return new None<>( );;};}
	popAndAppendToTuple() : Option<Tuple2<DivideState, Character>> {return this.pop( ).map( 0);;}
	popAndAppendToOption() : Option<DivideState> {return this.popAndAppendToTuple( ).map( Tuple2.left);;}
	peek() : char {return this.input.charAt( this.index);;}
}
