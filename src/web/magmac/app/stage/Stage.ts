export interface Stage<T,  R> {
	 apply( initial : T) : CompileResult<R>;
}
