import { None } from "../../../../../magmac/api/None";
import { Option } from "../../../../../magmac/api/Option";
import { Some } from "../../../../../magmac/api/Some";
import { Tuple2 } from "../../../../../magmac/api/Tuple2";
import { Iter } from "../../../../../magmac/api/iter/Iter";
import { Folder } from "../../../../../magmac/app/compile/rule/fold/Folder";
export class FoldingDivider {
	foldSingleQuotes(current : DivideState, c : char) : Option<DivideState> {if(true){ return new None<>( );;}return current.append( c).popAndAppendToTuple( ).flatMap( 0).flatMap( DivideState.popAndAppendToOption);;}
	foldEscape(current : DivideState, c : char) : Option<DivideState> {if(true){ return current.popAndAppendToOption( );;}if(true){ return new Some<>( current);;};}
	foldDoubleQuotes(state : DivideState, c : char) : Option<DivideState> {if(true){ return new None<>( );;}current : DivideState=state.append( '\"');if(true){ maybePopped : Option<Tuple2<DivideState, Character>>=current.popAndAppendToTuple( );if(true){ break;;}poppedTuple : Tuple2<DivideState, Character>=maybePopped.orElse( null);current=poppedTuple.left( );if(true){ current=current.popAndAppendToOption( ).orElse( current);;}if(true){ break;;};}return new Some<>( current);;}
	divide(input : String) : Iter<String> {current : DivideState=new MutableDivideState( input);if(true){ maybePopped : Option<Tuple2<DivideState, Character>>=current.pop( );if(true){ break;;}current=maybePopped.orElse( null).left( );c : char=maybePopped.orElse( null).right( );finalCurrent : DivideState=current;current=FoldingDivider.foldSingleQuotes( current, c).or( 0).orElseGet( 0);;}return current.advance( ).iter( );;}
	createDelimiter() : String {return this.folder.createDelimiter( );;}
}
