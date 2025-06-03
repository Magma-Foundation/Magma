export class FirstSelector {
	 FirstSelector( delimiter : String) : public {this.delimiter=delimiter;;}
	public select( list : List<String>) : Option<Tuple2<String, String>> {return list.popFirst( ).map( 0);;}
}
