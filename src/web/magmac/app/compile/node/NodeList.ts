export interface NodeList {
	 iter() : Iter<Node>;
	 findLast() : Option<Node>;
	 add( element : Node) : NodeList;
	 addAll( others : NodeList) : NodeList;
	 join( delimiter : String,  generator : Function<Node, CompileResult<String>>) : CompileResult<String>;
}
