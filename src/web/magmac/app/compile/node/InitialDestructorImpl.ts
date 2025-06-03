export class InitialDestructorImpl {
	 InitialDestructorImpl( node : Node) : public {this.node=node;;}
	public withNodeList( key : String,  deserializer : Deserializer<T>) : CompoundDestructor<List<T>> {return new CompoundDestructorImpl<>( this.node.removeNodeListOrError( key).flatMapValue( 0));;}
	public withString( key : String) : CompoundDestructor<String> {return new CompoundDestructorImpl<>( this.node.removeString( key));;}
	public withNode( key : String,  deserializer : Function<Node, CompileResult<T>>) : CompoundDestructor<T> {return new CompoundDestructorImpl<>( this.node.removeNodeOrError( key).flatMapValue( 0));;}
	public complete( supplier : Supplier<T>) : CompileResult<T> {if(true){ return CompileResults.NodeErr( "Fields still present", this.node);;}return CompileResults.Ok( supplier.get( ));;}
}
