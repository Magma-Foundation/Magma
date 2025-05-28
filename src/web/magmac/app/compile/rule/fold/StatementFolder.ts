import { DivideState } from "../../../../../magmac/app/compile/rule/divide/DivideState";
export class StatementFolder {
	fold(state : DivideState, c : char) : DivideState {
		appended : DivideState=state.append( c);
		if(';'==c&&appended.isLevel( )){ 
		return appended.advance( );}
		if('}'==c&&appended.isShallow( )){ 
		return appended.advance( ).exit( );}
		if('{'==c||'('==c){ 
		return appended.enter( );}
		if('}'==c||')'==c){ 
		return appended.exit( );}
		return appended;
	}
	createDelimiter() : String {
		return "";
	}
}
