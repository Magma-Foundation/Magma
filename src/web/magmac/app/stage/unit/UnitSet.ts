export interface UnitSet<T> {
	 iterValues() : Iter<T>;
	 iter() : Iter<Unit<T>>;
	 add( element : Unit<T>) : UnitSet<T>;
	 mapValuesToResult( deserializer : Function<T, CompileResult<R>>) : CompileResult<UnitSet<R>>;
	 mapValues( serializer : Function<T, R>) : UnitSet<R>;
}
