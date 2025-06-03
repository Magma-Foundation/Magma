import { DivideState } from "../../../../../magmac/app/compile/rule/divide/DivideState";
export class StatementFolder {
	fold(state : DivideState, c : char) : DivideState {appended : DivideState=state.append( c);if(true){ return appended.advance( );;}if(true){ return appended.advance( ).exit( );;}if(true){ return appended.enter( );;}if(true){ return appended.exit( );;}return appended;;}
	createDelimiter() : String {return "";;}
}
