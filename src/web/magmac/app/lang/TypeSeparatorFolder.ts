import { DivideState } from "../../../magmac/app/compile/rule/divide/DivideState";
import { Folder } from "../../../magmac/app/compile/rule/fold/Folder";
export class TypeSeparatorFolder {
	public fold( state() : DivideState,  c() : char) : DivideState {
		if(' '==c&&state.isLevel( )){ 
		return state.advance( );}
		 appended() : DivideState=state.append( c);
		if('<'==c){ 
		return appended.enter( );}
		if('>'==c){ 
		return appended.exit( );}
		return appended;
	}
	public createDelimiter() : String {
		return " ";
	}
}
