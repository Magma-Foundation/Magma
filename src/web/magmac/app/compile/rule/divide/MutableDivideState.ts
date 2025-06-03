import { None } from "../../../../../magmac/api/None";
import { Option } from "../../../../../magmac/api/Option";
import { Some } from "../../../../../magmac/api/Some";
import { Tuple2 } from "../../../../../magmac/api/Tuple2";
import { List } from "../../../../../magmac/api/collect/list/List";
import { Lists } from "../../../../../magmac/api/collect/list/Lists";
import { Iter } from "../../../../../magmac/api/iter/Iter";
export class MutableDivideState {
	MutableDivideState(segments : List<String>, buffer : StringBuilder, input : String) : private {this.segments=segments;this.buffer=buffer;this.input=input;this.depth=0;;}
	MutableDivideState(input : String) : public {this( Lists.empty( ), new StringBuilder( ), input);;}
	append(c : char) : DivideState {this.buffer.append( c);return this;;}
	advance() : DivideState {this.segments=this.segments.addLast( this.buffer.toString( ));this.buffer=new StringBuilder( );return this;;}
	iter() : Iter<String> {return this.segments.iter( );;}
	isLevel() : boolean {return 0==this.depth;;}
	enter() : DivideState {this.depth++;return this;;}
	exit() : DivideState {this.depth--;return this;;}
	isShallow() : boolean {return 1==this.depth;;}
	pop() : Option<Tuple2<DivideState, Character>> {if(true){ c : var=this.input.charAt( this.index);this.index++;return new Some<>( new Tuple2<DivideState, Character>( this, c));;}if(true){ return new None<>( );;};}
	popAndAppendToTuple() : Option<Tuple2<DivideState, Character>> {return this.pop( ).map( 0);;}
	popAndAppendToOption() : Option<DivideState> {return this.popAndAppendToTuple( ).map( Tuple2.left);;}
	peek() : char {return this.input.charAt( this.index);;}
}
