export class Joiner {
	 Joiner() : public {this( "");;}
	public createInitial() : Option<String> {return new None<>( );;}
	public fold( current : Option<String>,  element : String) : Option<String> {return new Some<>( current.map( 0).orElse( element));;}
}
