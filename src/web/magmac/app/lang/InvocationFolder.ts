import { DivideState } from "../../../magmac/app/compile/rule/divide/DivideState";
import { Folder } from "../../../magmac/app/compile/rule/fold/Folder";
export class InvocationFolder {
	fold(state : DivideState, c : char) : DivideState { DivideState appended=state.append( c);if('('==c&&appended.isLevel( )){ return appended.enter( ).advance( );}if('('==c){ return appended.enter( );}if(')'==c){ return appended.exit( );}return appended;}
	createDelimiter() : String {return "";}
}
