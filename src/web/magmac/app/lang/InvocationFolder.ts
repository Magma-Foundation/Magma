import { DivideState } from "../../../magmac/app/compile/rule/divide/DivideState";
import { Folder } from "../../../magmac/app/compile/rule/fold/Folder";
export class InvocationFolder {
	fold(state : DivideState, c : char) : DivideState {appended : var=state.append( c);if(true){ return appended.enter( ).advance( );;}if(true){ return appended.enter( );;}if(true){ return appended.exit( );;}return appended;;}
	createDelimiter() : String {return "";;}
}
