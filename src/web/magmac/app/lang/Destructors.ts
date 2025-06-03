export class Destructors {
	public static destructWithType( type : String,  node : Node) : Option<InitialDestructor> {if(true){ return new Some<>( new InitialDestructorImpl( node));;}return new None<>( );;}
	public static destruct( node : Node) : InitialDestructor {return new InitialDestructorImpl( node);;}
}
