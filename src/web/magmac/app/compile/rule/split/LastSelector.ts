export class LastSelector {
	 LastSelector( delimiter : String) : public {this.delimiter=delimiter;;}
	public select( list : List<String>) : Option<Tuple2<String, String>> {return list.popLast( ).map( this.merge);;}
	private merge( tuple : Tuple2<List<String>, String>) : Tuple2<String, String> { let joined : var=tuple.left( ).iter( ).collect( new Joiner( this.delimiter)).orElse( "");return new Tuple2<String, String>( joined, tuple.right( ));;}
}
