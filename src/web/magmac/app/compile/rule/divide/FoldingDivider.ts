import { None } from "../../../../../magmac/api/None";
import { Option } from "../../../../../magmac/api/Option";
import { Some } from "../../../../../magmac/api/Some";
import { Tuple2 } from "../../../../../magmac/api/Tuple2";
import { Iter } from "../../../../../magmac/api/iter/Iter";
import { Folder } from "../../../../../magmac/app/compile/rule/fold/Folder";
export class FoldingDivider {
	private static foldSingleQuotes( current : DivideState,  c : char) : Option<DivideState> {
		if('\''!=c){ 
		return new None<>( );}
		return current.append( c).popAndAppendToTuple( ).flatMap( ( tuple : Tuple2<DivideState, Character>) => FoldingDivider.foldEscape( current, tuple.right( ))).flatMap( ( state : DivideState) => state.popAndAppendToOption( ));
	}
	private static foldEscape( current : DivideState,  c : char) : Option<DivideState> {
		if('\\'==c){ 
		return current.popAndAppendToOption( );}
		else{ 
		return new Some<>( current);}
	}
	private static foldDoubleQuotes( state : DivideState,  c : char) : Option<DivideState> {
		if('\"'!=c){ 
		return new None<>( );}
		 current : DivideState=state.append( '\"');
		if(true){ 
		 maybePopped : Option<Tuple2<DivideState, Character>>=current.popAndAppendToTuple( );
		if(maybePopped.isEmpty( )){ 
		break;}
		 poppedTuple : Tuple2<DivideState, Character>=maybePopped.orElse( null);
		current=poppedTuple.left( );
		if('\\'==poppedTuple.right( )){ 
		current=current.popAndAppendToOption( ).orElse( current);}
		if('\"'==poppedTuple.right( )){ 
		break;}}
		return new Some<>( current);
	}
	public divide( input : String) : Iter<String> {
		 current : DivideState=new MutableDivideState( input);
		if(true){ 
		 maybePopped : Option<Tuple2<DivideState, Character>>=current.pop( );
		if(maybePopped.isEmpty( )){ 
		break;}
		current=maybePopped.orElse( null).left( );
		 c : char=maybePopped.orElse( null).right( );
		 finalCurrent : DivideState=current;
		current=FoldingDivider.foldSingleQuotes( current, c).or( ( )->FoldingDivider.foldDoubleQuotes( finalCurrent, c)).orElseGet( ( )->this.folder.fold( finalCurrent, c));}
		return current.advance( ).iter( );
	}
}
