import { None } from "../../../../../magmac/api/None";
import { Option } from "../../../../../magmac/api/Option";
import { Some } from "../../../../../magmac/api/Some";
import { Tuple2 } from "../../../../../magmac/api/Tuple2";
import { Iter } from "../../../../../magmac/api/iter/Iter";
import { Folder } from "../../../../../magmac/app/compile/rule/fold/Folder";
export class FoldingDivider {
	private static foldSingleQuotes( current : DivideState,  c : char) : Option<DivideState> {if(true){ return new None<>( );;}return current.append( c).popAndAppendToTuple( ).flatMap( 0).flatMap( DivideState.popAndAppendToOption);;}
	private static foldEscape( current : DivideState,  c : char) : Option<DivideState> {if(true){ return current.popAndAppendToOption( );;}if(true){ return new Some<>( current);;};}
	private static foldDoubleQuotes( state : DivideState,  c : char) : Option<DivideState> {if(true){ return new None<>( );;} let current : var=state.append( '\"');if(true){  let maybePopped : var=current.popAndAppendToTuple( );if(true){ break;;} let poppedTuple : var=maybePopped.orElse( null);current=poppedTuple.left( );if(true){ current=current.popAndAppendToOption( ).orElse( current);;}if(true){ break;;};}return new Some<>( current);;}
	public divide( input : String) : Iter<String> { let current : DivideState=new MutableDivideState( input);if(true){  let maybePopped : var=current.pop( );if(true){ break;;}current=maybePopped.orElse( null).left( ); let c : char=maybePopped.orElse( null).right( ); let finalCurrent : var=current;current=FoldingDivider.foldSingleQuotes( current, c).or( ( )->FoldingDivider.foldDoubleQuotes( finalCurrent, c)).orElseGet( ( )->this.folder.fold( finalCurrent, c));;}return current.advance( ).iter( );;}
	public createDelimiter() : String {return this.folder.createDelimiter( );;}
}
