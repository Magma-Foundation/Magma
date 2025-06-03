import { DivideState } from "../../../magmac/app/compile/rule/divide/DivideState";
import { Folder } from "../../../magmac/app/compile/rule/fold/Folder";
export class BlockFolder {
	public fold( state : DivideState,  c : char) : DivideState { let appended : var=state.append( c);if(true){ return appended.advance( );;}return appended;;}
	public createDelimiter() : String {return "";;}
}
