export interface Deserializer<T> {
	 deserialize( node : Node) : Option<CompileResult<T>>;
}
