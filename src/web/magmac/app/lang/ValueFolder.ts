import { DivideState } from "../../../magmac/app/compile/rule/divide/DivideState";
import { Folder } from "../../../magmac/app/compile/rule/fold/Folder";
export class ValueFolder {
	public fold( state : DivideState,  c : char) : DivideState {if(true){ return state.advance( );;} let appended : var=state.append( c);if(true){ if(true){ return state.popAndAppendToOption( ).orElse( state);;};}if(true){ return appended.enter( );;}if(true){ return appended.exit( );;}return appended;;}
	public createDelimiter() : String {return ", ";;}
}
