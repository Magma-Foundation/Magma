export interface ParseUnit<T> {
	 toLocationUnit() : Unit<T>;
	 merge( merge : BiFunction<ParseState, T, R>) : R;
	 retainWithList() : ParseUnit<NodeList>;
	 left() : ParseState;
	 right() : T;
}
