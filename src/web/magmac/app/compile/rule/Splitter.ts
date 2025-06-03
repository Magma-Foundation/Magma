export interface Splitter {
	 split( input : String) : Option<Tuple2<String, String>>;
	 createMessage() : String;
	 merge( leftString : String,  rightString : String) : String;
}
