import { None } from "../../../../../magmac/api/None";
import { Option } from "../../../../../magmac/api/Option";
import { Some } from "../../../../../magmac/api/Some";
import { Tuple2 } from "../../../../../magmac/api/Tuple2";
import { Iter } from "../../../../../magmac/api/iter/Iter";
import { Folder } from "../../../../../magmac/app/compile/rule/fold/Folder";
export class FoldingDivider {
	foldSingleQuotes(current : DivideState, c : char) : Option<DivideState> {if(true){ return new None<>( );;}return 0.append( 0).popAndAppendToTuple( ).flatMap( 0).flatMap( 0.popAndAppendToOption);;}
	foldEscape(current : DivideState, c : char) : Option<DivideState> {if(true){ return 0.popAndAppendToOption( );;}if(true){ return new Some<>( 0);;};}
	foldDoubleQuotes(state : DivideState, c : char) : Option<DivideState> {if(true){ return new None<>( );;}break;if(true){ break;if(true){ break;;}break;break;if(true){ break;;}if(true){ break;;};}return new Some<>( 0);;}
	divide(input : String) : Iter<String> {break;if(true){ break;if(true){ break;;}break;break;break;break;;}return 0.advance( ).iter( );;}
	createDelimiter() : String {return 0.folder.createDelimiter( );;}
}
