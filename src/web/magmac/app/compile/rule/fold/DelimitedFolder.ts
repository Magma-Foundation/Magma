export class DelimitedFolder {
	 DelimitedFolder( delimter : char) : public {this.delimter=delimter;;}
	public fold( state : DivideState,  c : char) : DivideState {if(true){ return state.advance( );;}return state.append( c);;}
	public createDelimiter() : String {return String.valueOf( this.delimter);;}
}
