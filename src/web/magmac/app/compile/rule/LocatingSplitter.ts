export class LocatingSplitter {
	public split( input : String) : Option<Tuple2<String, String>> {return this.locator.locate( input, this.infix).map( 0);;}
	public createMessage() : String {return "Infix '" + this.infix + "' not present";;}
	public merge( leftString : String,  rightString : String) : String {return leftString+this.infix+rightString;;}
}
