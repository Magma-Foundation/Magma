export interface List<T> {
	 addLast( element : T) : List<T>;
	 iter() : Iter<T>;
	 addAllLast( others : List<T>) : List<T>;
	 removeAll( others : List<T>) : List<T>;
	 get( index : int) : T;
	 sort( sorter : BiFunction<T, T, Integer>) : List<T>;
	 findLast() : Option<T>;
	 contains( element : T) : boolean;
	 size() : int;
	 popLast() : Option<Tuple2<List<T>, T>>;
	 popFirst() : Option<Tuple2<T, List<T>>>;
	 addFirst( element : T) : List<T>;
	 isEmpty() : boolean;
	 iterWithIndices() : Iter<Tuple2<Integer, T>>;
}
