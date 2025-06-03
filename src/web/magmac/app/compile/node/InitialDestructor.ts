export interface InitialDestructor {
	 withNodeList( key : String,  deserializer : Deserializer<T>) : CompoundDestructor<List<T>>;
	 withString( key : String) : CompoundDestructor<String>;
	 withNode( key : String,  deserializer : Function<Node, CompileResult<T>>) : CompoundDestructor<T>;
	 complete( supplier : Supplier<T>) : CompileResult<T>;
}
