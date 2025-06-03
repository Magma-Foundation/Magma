export interface Deserializer<T> {
	 deserialize( node : Node) : CompileResult<T>;
}
