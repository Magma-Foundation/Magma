/*[
	Actual, 
	Characters, 
	Collector, 
	Console, 
	EmptyHead, 
	Err, 
	Files, 
	FlatMapHead, 
	Head, 
	HeadedQuery, 
	IOError, 
	Joiner, 
	List, 
	ListCollector, 
	Lists, 
	MapHead, 
	Namespace, 
	None, 
	Ok, 
	Option, 
	Path, 
	Queries, 
	Query, 
	RangeHead, 
	Result, 
	SingleHead, 
	Some, 
	Strings, 
	Tuple2, 
	Tuple2Impl, 
	Type, 
	ZipHead
]*/
export interface Type {
	generate(): string;
	isFunctional(): boolean;
	isVar(): boolean;
	generateBeforeName(): string;
}
