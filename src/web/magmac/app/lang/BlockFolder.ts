import { DivideState } from "../../../magmac/app/compile/rule/divide/DivideState";
import { Folder } from "../../../magmac/app/compile/rule/fold/Folder";
export class BlockFolder {
	fold(state : DivideState, c : char) : DivideState { DivideState appended=state.append( c);if('{'==c){ return appended.advance( );}return appended;}
	createDelimiter() : String {return "";}
}
