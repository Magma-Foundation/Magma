import { None } from "../../../../../magmac/api/None";
import { Option } from "../../../../../magmac/api/Option";
import { Some } from "../../../../../magmac/api/Some";
import { Tuple2 } from "../../../../../magmac/api/Tuple2";
import { Iter } from "../../../../../magmac/api/iter/Iter";
import { Folder } from "../../../../../magmac/app/compile/rule/fold/Folder";
export class FoldingDivider {
	foldSingleQuotes(current : DivideState, c : char) : Option<DivideState> {if('\''!=c){ return new None<>( );}return current.append( c).popAndAppendToTuple( ).flatMap( (Tuple2<DivideState, Character> tuple) ->FoldingDivider.foldEscape( current, tuple.right( ))).flatMap( (DivideState state) ->state.popAndAppendToOption( ));}
	foldEscape(current : DivideState, c : char) : Option<DivideState> {if('\\'==c){ return current.popAndAppendToOption( );}else{ return new Some<>( current);}}
	foldDoubleQuotes(state : DivideState, c : char) : Option<DivideState> {if('\"'!=c){ return new None<>( );} DivideState current=state.append( '\"');if(true){  Option<Tuple2<DivideState, Character>> maybePopped=current.popAndAppendToTuple( );if(maybePopped.isEmpty( )){ break;} Tuple2<DivideState, Character> poppedTuple=maybePopped.orElse( null);current=poppedTuple.left( );if('\\'==poppedTuple.right( )){ current=current.popAndAppendToOption( ).orElse( current);}if('\"'==poppedTuple.right( )){ break;}}return new Some<>( current);}
	divide(input : String) : Iter<String> { DivideState current=new MutableDivideState( input);if(true){  Option<Tuple2<DivideState, Character>> maybePopped=current.pop( );if(maybePopped.isEmpty( )){ break;}current=maybePopped.orElse( null).left( ); char c=maybePopped.orElse( null).right( ); DivideState finalCurrent=current;current=FoldingDivider.foldSingleQuotes( current, c).or( () ->FoldingDivider.foldDoubleQuotes( finalCurrent, c)).orElseGet( () ->this.folder.fold( finalCurrent, c));}return current.advance( ).iter( );}
}
