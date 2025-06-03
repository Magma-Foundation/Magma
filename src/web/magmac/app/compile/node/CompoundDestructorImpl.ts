export class CompoundDestructorImpl<T> {
	 CompoundDestructorImpl( result : CompileResult<Tuple2<Node, T>>) : public {this.current=result;;}
	public complete( mapper : Function<T, R>) : CompileResult<R> {return this.current.flatMapValue( 0);;}
	public withNodeList( key : String,  deserializer : Function<Node, CompileResult<R>>) : CompoundDestructor<Tuple2<T, List<R>>> {return new CompoundDestructorImpl<>( this.current.flatMapValue( 0));;}
	public withNode( key : String,  deserializer : Function<Node, CompileResult<R>>) : CompoundDestructor<Tuple2<T, R>> {return new CompoundDestructorImpl<Tuple2<T, R>>( this.current.flatMapValue( 0));;}
	public withNodeOptionally( key : String,  deserializer : Function<Node, CompileResult<R>>) : CompoundDestructor<Tuple2<T, Option<R>>> {return new CompoundDestructorImpl<>( this.current.flatMapValue( ( 0)));;}
	public withNodeListOptionally( key : String,  deserializer : Function<Node, CompileResult<R>>) : CompoundDestructor<Tuple2<T, Option<List<R>>>> {return new CompoundDestructorImpl<>( this.current.flatMapValue( ( 0)));;}
	private getTuple2CompileResult( deserializer : Function<Node, CompileResult<R>>,  inner : Tuple2<Node, T>,  tuple : Tuple2<Node, Node>) : CompileResult<Tuple2<Node, Tuple2<T, Option<R>>>> {return deserializer.apply( tuple.right( )).flatMapValue( 0);;}
	private mapElements( current : Node,  elements : NodeList,  deserializer : Function<Node, CompileResult<R>>,  more : T) : CompileResult<Tuple2<Node, Tuple2<T, Option<List<R>>>>> {return elements.iter( ).map( deserializer).collect( new CompileResultCollector<>( new ListCollector<>( ))).mapValue( 0);;}
}
