export interface Folder {
	 fold( state : DivideState,  c : char) : DivideState;
	 createDelimiter() : String;
}
