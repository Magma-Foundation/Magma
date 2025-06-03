export class TypeSeparatorFolder {
	public fold( state : DivideState,  c : char) : DivideState {if(true){ return state.advance( );;} let appended : var=state.append( c);if(true){ return appended.enter( );;}if(true){ return appended.exit( );;}return appended;;}
	public createDelimiter() : String {return " ";;}
}
