import { None } from "../../../../../magmac/api/None";
import { Option } from "../../../../../magmac/api/Option";
import { Some } from "../../../../../magmac/api/Some";
import { Tuple2 } from "../../../../../magmac/api/Tuple2";
import { List } from "../../../../../magmac/api/collect/list/List";
import { Lists } from "../../../../../magmac/api/collect/list/Lists";
import { Iter } from "../../../../../magmac/api/iter/Iter";
export class MutableDivideState {
	 MutableDivideState( segments : List<String>,  buffer : StringBuilder,  input : String) : private {this.segments=segments;this.buffer=buffer;this.input=input;this.depth=0;;}
	 MutableDivideState( input : String) : public {this( Lists.empty( ), new StringBuilder( ), input);;}
	public append( c : char) : DivideState {this.buffer.append( c);return this;;}
	public advance() : DivideState {this.segments=this.segments.addLast( this.buffer.toString( ));this.buffer=new StringBuilder( );return this;;}
	public iter() : Iter<String> {return this.segments.iter( );;}
	public isLevel() : boolean {return 0==this.depth;;}
	public enter() : DivideState {this.depth++;return this;;}
	public exit() : DivideState {this.depth--;return this;;}
	public isShallow() : boolean {return 1==this.depth;;}
	public pop() : Option<Tuple2<DivideState, Character>> {if(true){  let c : var=this.input.charAt( this.index);this.index++;return new Some<>( new Tuple2<DivideState, Character>( this, c));;}if(true){ return new None<>( );;};}
	public popAndAppendToTuple() : Option<Tuple2<DivideState, Character>> {return this.pop( ).map( 0);;}
	public popAndAppendToOption() : Option<DivideState> {return this.popAndAppendToTuple( ).map( Tuple2.left);;}
	public peek() : char {return this.input.charAt( this.index);;}
}
