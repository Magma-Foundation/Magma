export interface TypedDeserializer<T> {
	 deserialize( node : Node) : Option<CompileResult<T>>;
}
