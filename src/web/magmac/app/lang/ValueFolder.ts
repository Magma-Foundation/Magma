import { DivideState } from "../../../magmac/app/compile/rule/divide/DivideState";
import { Folder } from "../../../magmac/app/compile/rule/fold/Folder";
export class ValueFolder {
	public fold( state : DivideState,  c : char) : DivideState {
		if(','==c&&state.isLevel( )){ 
		return state.advance( );}
		 appended : DivideState=state.append( c);
		if('-'==c){ 
		if('>'==state.peek( )){ 
		return state.popAndAppendToOption( ).orElse( state);}}
		if('<'==c||'('==c||'{'==c){ 
		return appended.enter( );}
		if('>'==c||')'==c||'}'==c){ 
		return appended.exit( );}
		return appended;
	}
	public createDelimiter() : String {
		return ", ";
	}
}
