export class StatementFolder {
	public fold( state : DivideState,  c : char) : DivideState { let appended : var=state.append( c);if(true){ return appended.advance( );;}if(true){ return appended.advance( ).exit( );;}if(true){ return appended.enter( );;}if(true){ return appended.exit( );;}return appended;;}
	public createDelimiter() : String {return "";;}
}
