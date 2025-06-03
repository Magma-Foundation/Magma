import { None } from "../../../../../magmac/api/None";
import { Option } from "../../../../../magmac/api/Option";
import { Some } from "../../../../../magmac/api/Some";
import { Tuple2 } from "../../../../../magmac/api/Tuple2";
import { Iter } from "../../../../../magmac/api/iter/Iter";
import { Folder } from "../../../../../magmac/app/compile/rule/fold/Folder";
export class FoldingDivider {
	foldSingleQuotes(current : DivideState, c : char) : Option<DivideState> {if(true){ return new None<>( );;}return current.append( c).popAndAppendToTuple( ).flatMap( 0).flatMap( DivideState.popAndAppendToOption);;}
	foldEscape(current : DivideState, c : char) : Option<DivideState> {if(true){ return current.popAndAppendToOption( );;}if(true){ return new Some<>( current);;};}
	foldDoubleQuotes(state : DivideState, c : char) : Option<DivideState> {if(true){ return new None<>( );;}break;if(true){ break;if(true){ break;;}break;break;if(true){ break;;}if(true){ break;;};}return new Some<>( current);;}
	divide(input : String) : Iter<String> {break;if(true){ break;if(true){ break;;}break;break;break;break;;}return current.advance( ).iter( );;}
	createDelimiter() : String {return this.folder.createDelimiter( );;}
}
