/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */interface Tuple2<A, B>/*   */ {
	left() : A;
	right() : B;
}
enum OptionVariant {
	Some,
	None
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private sealed */interface Option<T>/*  */ {
	_OptionVariant : OptionVariant;
	map<R>(mapper : (arg0 : T) => R) : Option<R>;
	isPresent() : boolean;
	orElse(other : T) : T;
	filter(predicate : (arg0 : T) => boolean) : Option<T>;
	orElseGet(supplier : () => T) : T;
	or(other : () => Option<T>) : Option<T>;
	flatMap<R>(mapper : (arg0 : T) => Option<R>) : Option<R>;
	isEmpty() : boolean;
	and<R>(other : () => Option<R>) : Option<[T, R]>;
	ifPresent(consumer : (arg0 : T) => void) : void;
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */interface Collector<T, C>/*   */ {
	createInitial() : C;
	fold(current : C, element : T) : C;
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */interface Query<T>/*   */ {
	fold<R>(initial : R, folder : (arg0 : R, arg1 : T) => R) : R;
	map<R>(mapper : (arg0 : T) => R) : Query<R>;
	collect<R>(collector : Collector<T, R>) : R;
	filter(predicate : (arg0 : T) => boolean) : Query<T>;
	next() : Option<T>;
	flatMap<R>(f : (arg0 : T) => Query<R>) : Query<R>;
	zip<R>(other : Query<R>) : Query<[T, R]>;
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */interface List<T>/*   */ {
	addLast(element : T) : List<T>;
	query() : Query<T>;
	removeLast() : Option<[List<T>, T]>;
	get(index : number) : Option<T>;
	size() : number;
	isEmpty() : boolean;
	addFirst(element : T) : List<T>;
	iterateWithIndices() : Query<[number, T]>;
	removeFirst() : Option<[T, List<T>]>;
	addAllLast(others : List<T>) : List<T>;
	last() : Option<T>;
	iterateReversed() : Query<T>;
	mapLast(mapper : (arg0 : T) => T) : List<T>;
	addAllFirst(others : List<T>) : List<T>;
	contains(element : T) : boolean;
	sort(sorter : (arg0 : T, arg1 : T) => number) : List<T>;
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */interface Head<T>/*   */ {
	next() : Option<T>;
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */interface Map<K, V>/*   */ {
	find(key : K) : Option<V>;
	with(key : K, value : V) : Map<K, V>;
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private sealed */interface Type/*  */ extends /* Argument */ {
	generate() : string;
	replace(mapping : Map<string, Type>) : Type;
	findName() : string;
}
enum ArgumentVariant {
	Type,
	Value,
	Whitespace
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private sealed */interface Argument/*  */ {
	_ArgumentVariant : ArgumentVariant;
}
enum ParameterVariant {
	Definition,
	Placeholder,
	Whitespace
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private sealed */interface Parameter/*  */ {
	_ParameterVariant : ParameterVariant;
}
enum ValueVariant {
	BooleanValue,
	Cast,
	DataAccess,
	IndexValue,
	Invokable,
	Lambda,
	Not,
	Operation,
	Placeholder,
	StringValue,
	SymbolValue,
	TupleNode
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private sealed */interface Value/*  */ extends /* LambdaValue */, /* Caller */, Argument {
	_ValueVariant : ValueVariant;
	generate() : string;
	type() : Type;
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */interface LambdaValue/*  */ {
	generate() : string;
}
enum CallerVariant {
	ConstructionCaller,
	Value
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private sealed */interface Caller/*  */ {
	_CallerVariant : CallerVariant;
	generate() : string;
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */interface BaseType/*  */ {
	hasVariant(name : string) : boolean;
	findName() : string;
}
enum FindableTypeVariant {
	ObjectType,
	Placeholder,
	Template
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private sealed */interface FindableType/*  */ extends Type {
	_FindableTypeVariant : FindableTypeVariant;
	find(name : string) : Option<Type>;
	findBase() : Option<BaseType>;
}
enum DefinitionVariant {
	ImmutableDefinition
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private sealed */interface Definition/*  */ extends Parameter, /* Header */, /* StatementValue */ {
	_DefinitionVariant : DefinitionVariant;
	generate() : string;
	mapType(mapper : (arg0 : Type) => Type) : Definition;
	generateWithParams(joinedParameters : string) : string;
	createDefinition(paramTypes : List<Type>) : Definition;
	findName() : string;
	findType() : Type;
	containsAnnotation(annotation : string) : boolean;
	removeAnnotations() : Definition;
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */interface Header/*  */ {
	createDefinition(paramTypes : List<Type>) : Definition;
	generateWithParams(joinedParameters : string) : string;
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */interface ClassSegment/*  */ {
	generate() : string;
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */interface FunctionSegment/*  */ {
	generate() : string;
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */interface BlockHeader/*  */ {
	generate() : string;
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */interface StatementValue/*  */ {
	generate() : string;
}
enum IncompleteClassSegmentVariant {
	ClassDefinition,
	ClassInitialization,
	EnumValues,
	IncompleteClassSegmentWrapper,
	MethodPrototype,
	Placeholder,
	StructurePrototype,
	Whitespace
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private sealed */interface IncompleteClassSegment/*  */ {
	_IncompleteClassSegmentVariant : IncompleteClassSegmentVariant;
	maybeCreateDefinition() : Option<Definition>;
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private @ */interface Actual/*  */ {
}
enum ResultVariant {
	Ok,
	Err
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private sealed */interface Result<T, X>/*  */ {
	_ResultVariant : ResultVariant;
	mapValue<R>(mapper : (arg0 : T) => R) : Result<R, X>;
	match<R>(whenOk : (arg0 : T) => R, whenErr : (arg0 : X) => R) : R;
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */interface IOError/*  */ {
	display() : string;
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */interface Path/*  */ {
	readString() : Result<string, IOError>;
	writeString(output : string) : Option<IOError>;
	resolve(childName : string) : Path;
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private static final */class None<T>/*  */ implements Option<T> {
	_OptionVariant : OptionVariant = OptionVariant.None/* : OptionVariant */;
	public map<R>(mapper : (arg0 : T) => R) : Option<R> {
		return new None()/* : None */;
	}
	public isPresent() : boolean {
		return false;
	}
	public orElse(other : T) : T {
		return other/* : T */;
	}
	public filter(predicate : (arg0 : T) => boolean) : Option<T> {
		return new None()/* : None */;
	}
	public orElseGet(supplier : () => T) : T {
		return supplier/* : () => T */()/* : T */;
	}
	public or(other : () => Option<T>) : Option<T> {
		return other/* : () => Option<T> */()/* : Option<T> */;
	}
	public flatMap<R>(mapper : (arg0 : T) => Option<R>) : Option<R> {
		return new None()/* : None */;
	}
	public isEmpty() : boolean {
		return true;
	}
	public and<R>(other : () => Option<R>) : Option<[T, R]> {
		return new None()/* : None */;
	}
	public ifPresent(consumer : (arg0 : T) => void) : void {
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */class Some<T>/*  */ implements Option<T> {
	value : T;
	constructor (value : T) {
		this/* : unknown */.value/* : unknown */ = value/* : unknown */;
	}
	_OptionVariant : OptionVariant = OptionVariant.Some/* : OptionVariant */;
	public map<R>(mapper : (arg0 : T) => R) : Option<R> {
		return new Some(mapper/* : (arg0 : T) => R */(this/* : Some */.value/* : T */)/* : R */)/* : Some */;
	}
	public isPresent() : boolean {
		return true;
	}
	public orElse(other : T) : T {
		return this/* : Some */.value/* : T */;
	}
	public filter(predicate : (arg0 : T) => boolean) : Option<T> {
		if (predicate/* : (arg0 : T) => boolean */(this/* : Some */.value/* : T */)/* : boolean */){
			return this/* : Some */;
		}
		return new None()/* : None */;
	}
	public orElseGet(supplier : () => T) : T {
		return this/* : Some */.value/* : T */;
	}
	public or(other : () => Option<T>) : Option<T> {
		return this/* : Some */;
	}
	public flatMap<R>(mapper : (arg0 : T) => Option<R>) : Option<R> {
		return mapper/* : (arg0 : T) => Option<R> */(this/* : Some */.value/* : T */)/* : Option<R> */;
	}
	public isEmpty() : boolean {
		return false;
	}
	public and<R>(other : () => Option<R>) : Option<[T, R]> {
		return other/* : () => Option<R> */()/* : Option<R> */.map/* : (arg0 : (arg0 : R) => R) => Option<R> */((otherValue : R) => [this/* : Some */.value/* : T */, otherValue/* : R */])/* : Option<R> */;
	}
	public ifPresent(consumer : (arg0 : T) => void) : void {
		/* consumer.accept(this.value) */;
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private static */class SingleHead<T>/*  */ implements Head<T> {
	readonly retrievableValue : T;
	retrieved : boolean;
	constructor (retrievableValue : T) {
		this/* : SingleHead */.retrievableValue/* : T */ = retrievableValue/* : T */;
		this/* : SingleHead */.retrieved/* : boolean */ = false;
	}
	public next() : Option<T> {
		if (this/* : SingleHead */.retrieved/* : boolean */){
			return new None()/* : None */;
		}
		this/* : SingleHead */.retrieved/* : boolean */ = true;
		return new Some(this/* : SingleHead */.retrievableValue/* : T */)/* : Some */;
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private static */class EmptyHead<T>/*  */ implements Head<T> {
	public next() : Option<T> {
		return new None()/* : None */;
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */class HeadedQuery<T>/*  */ implements Query<T> {
	head : Head<T>;
	constructor (head : Head<T>) {
		this/* : unknown */.head/* : unknown */ = head/* : unknown */;
	}
	public fold<R>(initial : R, folder : (arg0 : R, arg1 : T) => R) : R {
		let current : R = initial/* : R */;
		while (true){
			let finalCurrent : R = current/* : R */;
			let option : Option<R> = this/* : HeadedQuery */.head/* : Head<T> */.next/* : () => Option<T> */()/* : Option<T> */.map/* : (arg0 : (arg0 : T) => R) => Option<R> */((inner : T) => folder/* : (arg0 : R, arg1 : T) => R */(finalCurrent/* : R */, inner/* : T */)/* : R */)/* : Option<R> */;
			if (option/* : Option<R> */._OptionVariant/* : unknown */ === OptionVariant.Some/* : unknown */){
				let some : Some<R> = option/* : Option<R> */ as Some<R>;
				current/* : R */ = some/* : Some<R> */.value/* : R */;
			}
			else {
				return current/* : R */;
			}
		}
	}
	public map<R>(mapper : (arg0 : T) => R) : Query<R> {
		return new HeadedQuery(new MapHead(this/* : HeadedQuery */.head/* : Head<T> */, mapper/* : (arg0 : T) => R */)/* : MapHead */)/* : HeadedQuery */;
	}
	public collect<R>(collector : Collector<T, R>) : R {
		return this/* : HeadedQuery */.fold/* : (arg0 : R, arg1 : (arg0 : R, arg1 : T) => R) => R */(collector/* : Collector<T, R> */.createInitial/* : () => R */()/* : R */, collector/* : Collector<T, R> */.fold/* : unknown */)/* : R */;
	}
	public filter(predicate : (arg0 : T) => boolean) : Query<T> {
		return this/* : HeadedQuery */.flatMap/* : (arg0 : (arg0 : T) => Query<R>) => Query<R> */((element : T) => {
			if (predicate/* : (arg0 : T) => boolean */(element/* : T */)/* : boolean */){
				return new HeadedQuery(new SingleHead(element/* : T */)/* : SingleHead */)/* : HeadedQuery */;
			}
			return new HeadedQuery(new EmptyHead()/* : EmptyHead */)/* : HeadedQuery */;
		})/* : Query<R> */;
	}
	public next() : Option<T> {
		return this/* : HeadedQuery */.head/* : Head<T> */.next/* : () => Option<T> */()/* : Option<T> */;
	}
	public flatMap<R>(f : (arg0 : T) => Query<R>) : Query<R> {
		return new HeadedQuery(new FlatMapHead(this/* : HeadedQuery */.head/* : Head<T> */, f/* : (arg0 : T) => Query<R> */)/* : FlatMapHead */)/* : HeadedQuery */;
	}
	public zip<R>(other : Query<R>) : Query<[T, R]> {
		return new HeadedQuery(new ZipHead(this/* : HeadedQuery */.head/* : Head<T> */, other/* : Query<R> */)/* : ZipHead */)/* : HeadedQuery */;
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private static */class RangeHead/*  */ implements Head<number> {
	readonly length : number;
	counter : number;
	constructor (length : number) {
		this/* : RangeHead */.length/* : number */ = length/* : number */;
		this/* : RangeHead */.counter/* : number */ = 0/* : number */;
	}
	public next() : Option<number> {
		if (this/* : RangeHead */.counter/* : number */ < this/* : RangeHead */.length/* : unknown */){
			let value : number = this/* : RangeHead */.counter/* : number */;
			/* this.counter++ */;
			return new Some(value/* : number */)/* : Some */;
		}
		return new None()/* : None */;
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private static */class Lists/*  */ {
	public static empty<T>() : List<T>;
	public static of<T>(elements : T[]) : List<T>;
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */class ImmutableDefinition/*  */ implements Definition {
	annotations : List<string>;
	modifiers : List<string>;
	name : string;
	type : Type;
	typeParams : List<string>;
	constructor (annotations : List<string>, modifiers : List<string>, name : string, type : Type, typeParams : List<string>) {
		this/* : unknown */.annotations/* : unknown */ = annotations/* : unknown */;
		this/* : unknown */.modifiers/* : unknown */ = modifiers/* : unknown */;
		this/* : unknown */.name/* : unknown */ = name/* : unknown */;
		this/* : unknown */.type/* : unknown */ = type/* : unknown */;
		this/* : unknown */.typeParams/* : unknown */ = typeParams/* : unknown */;
	}
	_DefinitionVariant : DefinitionVariant = DefinitionVariant.ImmutableDefinition/* : DefinitionVariant */;
	public static createSimpleDefinition(name : string, type : Type) : Definition {
		return new ImmutableDefinition(Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, name/* : string */, type/* : Type */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */)/* : ImmutableDefinition */;
	}
	public findName() : string {
		return this/* : ImmutableDefinition */.name/* : string */;
	}
	public findType() : Type {
		return this/* : ImmutableDefinition */.type/* : Type */;
	}
	public generate() : string {
		return this/* : ImmutableDefinition */.generateWithParams/* : (arg0 : string) => string */("")/* : string */;
	}
	generateType() : string {
		if (this/* : ImmutableDefinition */.type/* : Type */ === Primitive/* : Primitive */.Unknown/* : unknown */){
			return "";
		}
		return " : " + this/* : ImmutableDefinition */.type/* : unknown */.generate/* : unknown */()/* : unknown */;
	}
	joinModifiers() : string {
		return this/* : ImmutableDefinition */.modifiers/* : List<string> */.query/* : () => Query<string> */()/* : Query<string> */.map/* : (arg0 : (arg0 : string) => R) => Query<R> */((value : string) => value/* : string */ + " ")/* : Query<R> */.collect/* : unknown */(new Joiner("")/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
	}
	joinTypeParams() : string {
		return this/* : ImmutableDefinition */.typeParams/* : List<string> */.query/* : () => Query<string> */()/* : Query<string> */.collect/* : (arg0 : Collector<string, R>) => R */(new Joiner(", ")/* : Joiner */)/* : R */.map/* : unknown */((inner) => "<" + inner + ">")/* : unknown */.orElse/* : unknown */("")/* : unknown */;
	}
	public mapType(mapper : (arg0 : Type) => Type) : Definition {
		return new ImmutableDefinition(this/* : ImmutableDefinition */.annotations/* : List<string> */, this/* : ImmutableDefinition */.modifiers/* : List<string> */, this/* : ImmutableDefinition */.name/* : string */, mapper/* : (arg0 : Type) => Type */(this/* : ImmutableDefinition */.type/* : Type */)/* : Type */, this/* : ImmutableDefinition */.typeParams/* : List<string> */)/* : ImmutableDefinition */;
	}
	public generateWithParams(joinedParameters : string) : string {
		let joinedAnnotations = this/* : ImmutableDefinition */.annotations/* : List<string> */.query/* : () => Query<string> */()/* : Query<string> */.map/* : (arg0 : (arg0 : string) => R) => Query<R> */((value : string) => "@" + value + " ")/* : Query<R> */.collect/* : unknown */(Joiner/* : Joiner */.empty/* : () => Joiner */()/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		let joined : string = this/* : ImmutableDefinition */.joinTypeParams/* : () => string */()/* : string */;
		let before : string = this/* : ImmutableDefinition */.joinModifiers/* : () => string */()/* : string */;
		let typeString : string = this/* : ImmutableDefinition */.generateType/* : () => string */()/* : string */;
		return joinedAnnotations/* : unknown */ + before/* : string */ + this/* : ImmutableDefinition */.name/* : string */ + joined/* : string */ + joinedParameters/* : string */ + typeString/* : string */;
	}
	public createDefinition(paramTypes : List<Type>) : Definition {
		let type1 : Type = new FunctionType(paramTypes/* : List<Type> */, this/* : ImmutableDefinition */.type/* : Type */)/* : FunctionType */;
		return new ImmutableDefinition(this/* : ImmutableDefinition */.annotations/* : List<string> */, this/* : ImmutableDefinition */.modifiers/* : List<string> */, this/* : ImmutableDefinition */.name/* : string */, type1/* : Type */, this/* : ImmutableDefinition */.typeParams/* : List<string> */)/* : ImmutableDefinition */;
	}
	public containsAnnotation(annotation : string) : boolean {
		return this/* : ImmutableDefinition */.annotations/* : List<string> */.contains/* : (arg0 : string) => boolean */(annotation/* : string */)/* : boolean */;
	}
	public removeAnnotations() : Definition {
		return new ImmutableDefinition(Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, this/* : ImmutableDefinition */.modifiers/* : List<string> */, this/* : ImmutableDefinition */.name/* : string */, this/* : ImmutableDefinition */.type/* : Type */, this/* : ImmutableDefinition */.typeParams/* : List<string> */)/* : ImmutableDefinition */;
	}
	public toString() : string {
		return "ImmutableDefinition[" + "annotations=" + this/* : ImmutableDefinition */.annotations/* : List<string> */ + ", " + "maybeBefore=" + this/* : ImmutableDefinition */.modifiers/* : List<string> */ + ", " + "findName=" + this/* : ImmutableDefinition */.name/* : string */ + ", " + "findType=" + this/* : ImmutableDefinition */.type/* : Type */ + ", " + "typeParams=" + this/* : ImmutableDefinition */.typeParams/* : List<string> */ + "]";
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */class ObjectType/*  */ implements FindableType, BaseType {
	name : string;
	typeParams : List<string>;
	definitions : List<Definition>;
	variants : List<string>;
	constructor (name : string, typeParams : List<string>, definitions : List<Definition>, variants : List<string>) {
		this/* : unknown */.name/* : unknown */ = name/* : unknown */;
		this/* : unknown */.typeParams/* : unknown */ = typeParams/* : unknown */;
		this/* : unknown */.definitions/* : unknown */ = definitions/* : unknown */;
		this/* : unknown */.variants/* : unknown */ = variants/* : unknown */;
	}
	_FindableTypeVariant : FindableTypeVariant = FindableTypeVariant.ObjectType/* : FindableTypeVariant */;
	public generate() : string {
		return this/* : ObjectType */.name/* : string */;
	}
	public replace(mapping : Map<string, Type>) : Type {
		return new ObjectType(this/* : ObjectType */.name/* : string */, this/* : ObjectType */.typeParams/* : List<string> */, this/* : ObjectType */.definitions/* : List<Definition> */.query/* : () => Query<Definition> */()/* : Query<Definition> */.map/* : (arg0 : (arg0 : Definition) => R) => Query<R> */((definition : Definition) => definition/* : Definition */.mapType/* : (arg0 : (arg0 : Type) => Type) => Definition */((type : Type) => type/* : Type */.replace/* : (arg0 : Map<string, Type>) => Type */(mapping/* : Map<string, Type> */)/* : Type */)/* : Definition */)/* : Query<R> */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */, this/* : ObjectType */.variants/* : List<string> */)/* : ObjectType */;
	}
	public find(name : string) : Option<Type> {
		return this/* : ObjectType */.definitions/* : List<Definition> */.query/* : () => Query<Definition> */()/* : Query<Definition> */.filter/* : (arg0 : (arg0 : Definition) => boolean) => Query<Definition> */((definition : Definition) => definition/* : Definition */.findName/* : () => string */()/* : string */ === name/* : string */)/* : Query<Definition> */.map/* : (arg0 : (arg0 : T) => R) => Option<R> */(Definition/* : Definition */.findType/* : unknown */)/* : Option<R> */.next/* : unknown */()/* : unknown */;
	}
	public findBase() : Option<BaseType> {
		return new Some(this/* : ObjectType */)/* : Some */;
	}
	public hasVariant(name : string) : boolean {
		return this/* : ObjectType */.variants/* : List<string> */()/* : unknown */.contains/* : unknown */(name/* : string */)/* : unknown */;
	}
	public findName() : string {
		return this/* : ObjectType */.name/* : string */;
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */class TypeParam/*  */ implements Type {
	value : string;
	constructor (value : string) {
		this/* : unknown */.value/* : unknown */ = value/* : unknown */;
	}
	public generate() : string {
		return this/* : TypeParam */.value/* : string */;
	}
	public replace(mapping : Map<string, Type>) : Type {
		return mapping/* : Map<string, Type> */.find/* : (arg0 : string) => Option<Type> */(this/* : TypeParam */.value/* : string */)/* : Option<Type> */.orElse/* : (arg0 : Type) => Type */(this/* : TypeParam */)/* : Type */;
	}
	public findName() : string {
		return "";
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */class CompileState/*  */ {
	structures : List<string>;
	definitions : List<List<Definition>>;
	objectTypes : List<ObjectType>;
	structNames : List<[string, List<string>]>;
	typeParams : List<string>;
	typeRegister : Option<Type>;
	functionSegments : List<FunctionSegment>;
	constructor (structures : List<string>, definitions : List<List<Definition>>, objectTypes : List<ObjectType>, structNames : List<[string, List<string>]>, typeParams : List<string>, typeRegister : Option<Type>, functionSegments : List<FunctionSegment>) {
		this/* : unknown */.structures/* : unknown */ = structures/* : unknown */;
		this/* : unknown */.definitions/* : unknown */ = definitions/* : unknown */;
		this/* : unknown */.objectTypes/* : unknown */ = objectTypes/* : unknown */;
		this/* : unknown */.structNames/* : unknown */ = structNames/* : unknown */;
		this/* : unknown */.typeParams/* : unknown */ = typeParams/* : unknown */;
		this/* : unknown */.typeRegister/* : unknown */ = typeRegister/* : unknown */;
		this/* : unknown */.functionSegments/* : unknown */ = functionSegments/* : unknown */;
	}
	public static createInitial() : CompileState {
		return new CompileState(Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, Lists/* : Lists */.of/* : (arg0 : T[]) => List<T> */(Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */)/* : List<T> */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, new None()/* : None */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */)/* : CompileState */;
	}
	resolveValue(name : string) : Option<Type> {
		return this/* : CompileState */.definitions/* : List<List<Definition>> */.iterateReversed/* : () => Query<List<Definition>> */()/* : Query<List<Definition>> */.flatMap/* : (arg0 : (arg0 : List<Definition>) => Query<R>) => Query<R> */(List/* : List */.query/* : unknown */)/* : Query<R> */.filter/* : (arg0 : (arg0 : T) => boolean) => Option<T> */((definition : T) => definition/* : T */.findName/* : unknown */()/* : unknown */ === name/* : string */)/* : Option<T> */.next/* : unknown */()/* : unknown */.map/* : unknown */(Definition/* : Definition */.findType/* : unknown */)/* : unknown */;
	}
	public addStructure(structure : string) : CompileState {
		return new CompileState(this/* : CompileState */.structures/* : List<string> */.addLast/* : (arg0 : string) => List<string> */(structure/* : string */)/* : List<string> */, this/* : CompileState */.definitions/* : List<List<Definition>> */, this/* : CompileState */.objectTypes/* : List<ObjectType> */, this/* : CompileState */.structNames/* : List<[string, List<string>]> */, this/* : CompileState */.typeParams/* : List<string> */, this/* : CompileState */.typeRegister/* : Option<Type> */, this/* : CompileState */.functionSegments/* : List<FunctionSegment> */)/* : CompileState */;
	}
	public defineAll(definitions : List<Definition>) : CompileState {
		let defined : List<List<Definition>> = this/* : CompileState */.definitions/* : List<List<Definition>> */.mapLast/* : (arg0 : (arg0 : List<Definition>) => List<Definition>) => List<List<Definition>> */((frame : List<Definition>) => frame/* : List<Definition> */.addAllLast/* : (arg0 : List<Definition>) => List<Definition> */(definitions/* : List<Definition> */)/* : List<Definition> */)/* : List<List<Definition>> */;
		return new CompileState(this/* : CompileState */.structures/* : List<string> */, defined/* : List<List<Definition>> */, this/* : CompileState */.objectTypes/* : List<ObjectType> */, this/* : CompileState */.structNames/* : List<[string, List<string>]> */, this/* : CompileState */.typeParams/* : List<string> */, this/* : CompileState */.typeRegister/* : Option<Type> */, this/* : CompileState */.functionSegments/* : List<FunctionSegment> */)/* : CompileState */;
	}
	public resolveType(name : string) : Option<Type> {
		let maybe : Option<[string, List<string>]> = this/* : CompileState */.structNames/* : List<[string, List<string>]> */.last/* : () => Option<[string, List<string>]> */()/* : Option<[string, List<string>]> */.filter/* : (arg0 : (arg0 : [string, List<string>]) => boolean) => Option<[string, List<string>]> */((inner : [string, List<string>]) => inner/* : [string, List<string>] */[0/* : number */] === name/* : string */)/* : Option<[string, List<string>]> */;
		if (maybe/* : Option<[string, List<string>]> */._OptionVariant/* : unknown */ === OptionVariant.Some/* : unknown */){
			let some : Some<[string, List<string>]> = maybe/* : Option<[string, List<string>]> */ as Some<[string, List<string>]>;
			let found : [string, List<string>] = some/* : Some<[string, List<string>]> */.value/* : [string, List<string>] */;
			return new Some(new ObjectType(found/* : [string, List<string>] */[0/* : number */], this/* : CompileState */.typeParams/* : List<string> */, this/* : CompileState */.definitions/* : List<List<Definition>> */.last/* : () => Option<List<Definition>> */()/* : Option<List<Definition>> */.orElse/* : (arg0 : List<Definition>) => List<Definition> */(Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */)/* : List<Definition> */, found/* : [string, List<string>] */[1/* : number */])/* : ObjectType */)/* : Some */;
		}
		let maybeTypeParam = this/* : CompileState */.typeParams/* : List<string> */.query/* : () => Query<string> */()/* : Query<string> */.filter/* : (arg0 : (arg0 : string) => boolean) => Query<string> */((param : string) => param/* : string */ === name/* : string */)/* : Query<string> */.next/* : unknown */()/* : unknown */;
		if (maybeTypeParam/* : unknown */._UnknownVariant/* : unknown */ === UnknownVariant.Some/* : unknown */){
			let some : Some<string> = maybeTypeParam/* : unknown */ as Some<string>;
			return new Some(new TypeParam(some/* : Some<[string, List<string>]> */.value/* : [string, List<string>] */)/* : TypeParam */)/* : Some */;
		}
		return this/* : CompileState */.objectTypes/* : List<ObjectType> */.query/* : () => Query<ObjectType> */()/* : Query<ObjectType> */.filter/* : (arg0 : (arg0 : ObjectType) => boolean) => Query<ObjectType> */((type : ObjectType) => type/* : ObjectType */.name/* : string */ === name/* : string */)/* : Query<ObjectType> */.next/* : unknown */()/* : unknown */.map/* : unknown */((type) => type/* : ObjectType */)/* : unknown */;
	}
	public define(definition : Definition) : CompileState {
		return new CompileState(this/* : CompileState */.structures/* : List<string> */, this/* : CompileState */.definitions/* : List<List<Definition>> */.mapLast/* : (arg0 : (arg0 : List<Definition>) => List<Definition>) => List<List<Definition>> */((frame : List<Definition>) => frame/* : List<Definition> */.addLast/* : (arg0 : Definition) => List<Definition> */(definition/* : Definition */)/* : List<Definition> */)/* : List<List<Definition>> */, this/* : CompileState */.objectTypes/* : List<ObjectType> */, this/* : CompileState */.structNames/* : List<[string, List<string>]> */, this/* : CompileState */.typeParams/* : List<string> */, this/* : CompileState */.typeRegister/* : Option<Type> */, this/* : CompileState */.functionSegments/* : List<FunctionSegment> */)/* : CompileState */;
	}
	public pushStructName(definition : Tuple2Impl<string, List<string>>) : CompileState {
		return new CompileState(this/* : CompileState */.structures/* : List<string> */, this/* : CompileState */.definitions/* : List<List<Definition>> */, this/* : CompileState */.objectTypes/* : List<ObjectType> */, this/* : CompileState */.structNames/* : List<[string, List<string>]> */.addLast/* : (arg0 : [string, List<string>]) => List<[string, List<string>]> */(definition/* : Tuple2Impl<string, List<string>> */)/* : List<[string, List<string>]> */, this/* : CompileState */.typeParams/* : List<string> */, this/* : CompileState */.typeRegister/* : Option<Type> */, this/* : CompileState */.functionSegments/* : List<FunctionSegment> */)/* : CompileState */;
	}
	public withTypeParams(typeParams : List<string>) : CompileState {
		return new CompileState(this/* : CompileState */.structures/* : List<string> */, this/* : CompileState */.definitions/* : List<List<Definition>> */, this/* : CompileState */.objectTypes/* : List<ObjectType> */, this/* : CompileState */.structNames/* : List<[string, List<string>]> */, this/* : CompileState */.typeParams/* : List<string> */.addAllLast/* : (arg0 : List<string>) => List<string> */(typeParams/* : List<string> */)/* : List<string> */, this/* : CompileState */.typeRegister/* : Option<Type> */, this/* : CompileState */.functionSegments/* : List<FunctionSegment> */)/* : CompileState */;
	}
	public withExpectedType(type : Type) : CompileState {
		return new CompileState(this/* : CompileState */.structures/* : List<string> */, this/* : CompileState */.definitions/* : List<List<Definition>> */, this/* : CompileState */.objectTypes/* : List<ObjectType> */, this/* : CompileState */.structNames/* : List<[string, List<string>]> */, this/* : CompileState */.typeParams/* : List<string> */, new Some(type/* : Type */)/* : Some */, this/* : CompileState */.functionSegments/* : List<FunctionSegment> */)/* : CompileState */;
	}
	public popStructName() : CompileState {
		return new CompileState(this/* : CompileState */.structures/* : List<string> */, this/* : CompileState */.definitions/* : List<List<Definition>> */, this/* : CompileState */.objectTypes/* : List<ObjectType> */, this/* : CompileState */.structNames/* : List<[string, List<string>]> */.removeLast/* : () => Option<[List<T>, T]> */()/* : Option<[List<T>, T]> */.map/* : (arg0 : (arg0 : [List<T>, T]) => R) => Option<R> */(Tuple2/* : Tuple2 */.left/* : unknown */)/* : Option<R> */.orElse/* : unknown */(this/* : CompileState */.structNames/* : List<[string, List<string>]> */)/* : unknown */, this/* : CompileState */.typeParams/* : List<string> */, this/* : CompileState */.typeRegister/* : Option<Type> */, this/* : CompileState */.functionSegments/* : List<FunctionSegment> */)/* : CompileState */;
	}
	public enterDefinitions() : CompileState {
		return new CompileState(this/* : CompileState */.structures/* : List<string> */, this/* : CompileState */.definitions/* : List<List<Definition>> */.addLast/* : (arg0 : List<Definition>) => List<List<Definition>> */(Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */)/* : List<List<Definition>> */, this/* : CompileState */.objectTypes/* : List<ObjectType> */, this/* : CompileState */.structNames/* : List<[string, List<string>]> */, this/* : CompileState */.typeParams/* : List<string> */, this/* : CompileState */.typeRegister/* : Option<Type> */, this/* : CompileState */.functionSegments/* : List<FunctionSegment> */)/* : CompileState */;
	}
	public exitDefinitions() : CompileState {
		let removed = this/* : CompileState */.definitions/* : List<List<Definition>> */.removeLast/* : () => Option<[List<T>, T]> */()/* : Option<[List<T>, T]> */.map/* : (arg0 : (arg0 : [List<T>, T]) => R) => Option<R> */(Tuple2/* : Tuple2 */.left/* : unknown */)/* : Option<R> */.orElse/* : unknown */(this/* : CompileState */.definitions/* : List<List<Definition>> */)/* : unknown */;
		return new CompileState(this/* : CompileState */.structures/* : List<string> */, removed/* : unknown */, this/* : CompileState */.objectTypes/* : List<ObjectType> */, this/* : CompileState */.structNames/* : List<[string, List<string>]> */, this/* : CompileState */.typeParams/* : List<string> */, this/* : CompileState */.typeRegister/* : Option<Type> */, this/* : CompileState */.functionSegments/* : List<FunctionSegment> */)/* : CompileState */;
	}
	public addType(thisType : ObjectType) : CompileState {
		return new CompileState(this/* : CompileState */.structures/* : List<string> */, this/* : CompileState */.definitions/* : List<List<Definition>> */, this/* : CompileState */.objectTypes/* : List<ObjectType> */.addLast/* : (arg0 : ObjectType) => List<ObjectType> */(thisType/* : ObjectType */)/* : List<ObjectType> */, this/* : CompileState */.structNames/* : List<[string, List<string>]> */, this/* : CompileState */.typeParams/* : List<string> */, this/* : CompileState */.typeRegister/* : Option<Type> */, this/* : CompileState */.functionSegments/* : List<FunctionSegment> */)/* : CompileState */;
	}
	public addFunctionSegment(segment : FunctionSegment) : CompileState {
		return new CompileState(this/* : CompileState */.structures/* : List<string> */, this/* : CompileState */.definitions/* : List<List<Definition>> */, this/* : CompileState */.objectTypes/* : List<ObjectType> */, this/* : CompileState */.structNames/* : List<[string, List<string>]> */, this/* : CompileState */.typeParams/* : List<string> */, this/* : CompileState */.typeRegister/* : Option<Type> */, this/* : CompileState */.functionSegments/* : List<FunctionSegment> */.addLast/* : (arg0 : FunctionSegment) => List<FunctionSegment> */(segment/* : FunctionSegment */)/* : List<FunctionSegment> */)/* : CompileState */;
	}
	public clearFunctionSegments() : CompileState {
		return new CompileState(this/* : CompileState */.structures/* : List<string> */, this/* : CompileState */.definitions/* : List<List<Definition>> */, this/* : CompileState */.objectTypes/* : List<ObjectType> */, this/* : CompileState */.structNames/* : List<[string, List<string>]> */, this/* : CompileState */.typeParams/* : List<string> */, this/* : CompileState */.typeRegister/* : Option<Type> */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */)/* : CompileState */;
	}
	isCurrentStructName(stripped : string) : boolean {
		return stripped/* : string */ === this/* : CompileState */.structNames/* : List<[string, List<string>]> */.last/* : () => Option<[string, List<string>]> */()/* : Option<[string, List<string>]> */.map/* : (arg0 : (arg0 : [string, List<string>]) => R) => Option<R> */(Tuple2/* : Tuple2 */.left/* : unknown */)/* : Option<R> */.orElse/* : unknown */("")/* : unknown */;
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private static */class DivideState/*  */ {
	readonly input : string;
	readonly index : number;
	depth : number;
	segments : List<string>;
	buffer : string;
	constructor (input : string, index : number, segments : List<string>, buffer : string, depth : number) {
		this/* : DivideState */.segments/* : List<string> */ = segments/* : List<string> */;
		this/* : DivideState */.buffer/* : string */ = buffer/* : string */;
		this/* : DivideState */.depth/* : number */ = depth/* : number */;
		this/* : DivideState */.input/* : string */ = input/* : string */;
		this/* : DivideState */.index/* : number */ = index/* : number */;
	}
	public static createInitial(input : string) : DivideState {
		return new DivideState(input/* : string */, 0/* : number */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, "", 0/* : number */)/* : DivideState */;
	}
	advance() : DivideState {
		this/* : DivideState */.segments/* : List<string> */ = this/* : DivideState */.segments/* : List<string> */.addLast/* : (arg0 : string) => List<string> */(this/* : DivideState */.buffer/* : string */)/* : List<string> */;
		this/* : DivideState */.buffer/* : string */ = "";
		return this/* : DivideState */;
	}
	append(c : string) : DivideState {
		this/* : DivideState */.buffer/* : string */ = this/* : DivideState */.buffer/* : string */ + c/* : string */;
		return this/* : DivideState */;
	}
	public enter() : DivideState {
		/* this.depth++ */;
		return this/* : DivideState */;
	}
	public isLevel() : boolean {
		return this/* : DivideState */.depth/* : number */ === 0/* : number */;
	}
	public exit() : DivideState {
		/* this.depth-- */;
		return this/* : DivideState */;
	}
	public isShallow() : boolean {
		return this/* : DivideState */.depth/* : number */ === 1/* : number */;
	}
	public pop() : Option<[string, DivideState]> {
		if (this/* : DivideState */.index/* : number */ < Strings/* : Strings */.length/* : unknown */(this/* : DivideState */.input/* : string */)/* : unknown */){
			let c = this/* : DivideState */.input/* : string */.charAt/* : unknown */(this/* : DivideState */.index/* : number */)/* : unknown */;
			return new Some([c/* : unknown */, new DivideState(this/* : DivideState */.input/* : string */, this/* : DivideState */.index/* : number */ + 1/* : number */, this/* : DivideState */.segments/* : List<string> */, this/* : DivideState */.buffer/* : string */, this/* : DivideState */.depth/* : number */)/* : DivideState */])/* : Some */;
		}
		return new None()/* : None */;
	}
	public popAndAppendToTuple() : Option<[string, DivideState]> {
		return this/* : DivideState */.pop/* : () => Option<[string, DivideState]> */()/* : Option<[string, DivideState]> */.map/* : (arg0 : (arg0 : [string, DivideState]) => R) => Option<R> */((tuple : [string, DivideState]) => {
			let c = tuple/* : [string, DivideState] */[0/* : number */];
			let right = tuple/* : [string, DivideState] */[1/* : number */];
			return [c/* : unknown */, right/* : unknown */.append/* : unknown */(c/* : unknown */)/* : unknown */];
		})/* : Option<R> */;
	}
	public popAndAppendToOption() : Option<DivideState> {
		return this/* : DivideState */.popAndAppendToTuple/* : () => Option<[string, DivideState]> */()/* : Option<[string, DivideState]> */.map/* : (arg0 : (arg0 : [string, DivideState]) => R) => Option<R> */(Tuple2/* : Tuple2 */.right/* : unknown */)/* : Option<R> */;
	}
	public peek() : string {
		return this/* : DivideState */.input/* : string */.charAt/* : unknown */(this/* : DivideState */.index/* : number */)/* : unknown */;
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */class Joiner/*  */ implements Collector<string, Option<string>> {
	delimiter : string;
	constructor (delimiter : string) {
		this/* : unknown */.delimiter/* : unknown */ = delimiter/* : unknown */;
	}
	static empty() : Joiner {
		return new Joiner("")/* : Joiner */;
	}
	public createInitial() : Option<string> {
		return new None()/* : None */;
	}
	public fold(current : Option<string>, element : string) : Option<string> {
		return new Some(current/* : Option<string> */.map/* : (arg0 : (arg0 : string) => R) => Option<R> */((inner : string) => inner/* : string */ + this/* : Joiner */.delimiter/* : string */ + element/* : string */)/* : Option<R> */.orElse/* : unknown */(element/* : string */)/* : unknown */)/* : Some */;
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private static */class ListCollector<T>/*  */ implements Collector<T, List<T>> {
	public createInitial() : List<T> {
		return Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */;
	}
	public fold(current : List<T>, element : T) : List<T> {
		return current/* : List<T> */.addLast/* : (arg0 : T) => List<T> */(element/* : T */)/* : List<T> */;
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private static */class FlatMapHead<T, R>/*  */ implements Head<R> {
	readonly mapper : (arg0 : T) => Query<R>;
	readonly head : Head<T>;
	current : Option<Query<R>>;
	constructor (head : Head<T>, mapper : (arg0 : T) => Query<R>) {
		this/* : FlatMapHead */.mapper/* : (arg0 : T) => Query<R> */ = mapper/* : (arg0 : T) => Query<R> */;
		this/* : FlatMapHead */.current/* : Option<Query<R>> */ = new None()/* : None */;
		this/* : FlatMapHead */.head/* : Head<T> */ = head/* : Head<T> */;
	}
	public next() : Option<R> {
		while (true){
			if (this/* : FlatMapHead */.current/* : Option<Query<R>> */.isPresent/* : () => boolean */()/* : boolean */){
				let inner : Query<R> = this/* : FlatMapHead */.current/* : Option<Query<R>> */.orElse/* : (arg0 : Query<R>) => Query<R> */(/* null */)/* : Query<R> */;
				let maybe : Option<R> = inner/* : Query<R> */.next/* : () => Option<R> */()/* : Option<R> */;
				if (maybe/* : Option<R> */.isPresent/* : () => boolean */()/* : boolean */){
					return maybe/* : Option<R> */;
				}
				else {
					this/* : FlatMapHead */.current/* : Option<Query<R>> */ = new None()/* : None */;
				}
			}
			let outer : Option<T> = this/* : FlatMapHead */.head/* : Head<T> */.next/* : () => Option<T> */()/* : Option<T> */;
			if (outer/* : Option<T> */.isPresent/* : () => boolean */()/* : boolean */){
				this/* : FlatMapHead */.current/* : Option<Query<R>> */ = outer/* : Option<T> */.map/* : (arg0 : (arg0 : T) => R) => Option<R> */(this/* : FlatMapHead */.mapper/* : (arg0 : T) => Query<R> */)/* : Option<R> */;
			}
			else {
				return new None()/* : None */;
			}
		}
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */class ArrayType/*  */ implements Type {
	right : Type;
	constructor (right : Type) {
		this/* : unknown */.right/* : unknown */ = right/* : unknown */;
	}
	public generate() : string {
		return this/* : ArrayType */.right/* : Type */.generate/* : () => string */()/* : string */ + "[]";
	}
	public replace(mapping : Map<string, Type>) : Type {
		return this/* : ArrayType */;
	}
	public findName() : string {
		return "";
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private static final */class Whitespace/*  */ implements Argument, Parameter, ClassSegment, FunctionSegment, IncompleteClassSegment {
	_IncompleteClassSegmentVariant : IncompleteClassSegmentVariant = IncompleteClassSegmentVariant.Whitespace/* : IncompleteClassSegmentVariant */;
	_ParameterVariant : ParameterVariant = ParameterVariant.Whitespace/* : ParameterVariant */;
	_ArgumentVariant : ArgumentVariant = ArgumentVariant.Whitespace/* : ArgumentVariant */;
	public generate() : string {
		return "";
	}
	public maybeCreateDefinition() : Option<Definition> {
		return new None()/* : None */;
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private static */class Queries/*  */ {
	public static fromOption<T>(option : Option<T>) : Query<T> {
		let single : Option<Head<T>> = option/* : Option<T> */.map/* : (arg0 : (arg0 : T) => R) => Option<R> */(SingleHead/* : SingleHead */.new/* : unknown */)/* : Option<R> */;
		return new HeadedQuery(single/* : Option<Head<T>> */.orElseGet/* : (arg0 : () => Head<T>) => Head<T> */(EmptyHead/* : EmptyHead */.new/* : unknown */)/* : Head<T> */)/* : HeadedQuery */;
	}
	public static from<T>(elements : T[]) : Query<T> {
		return new HeadedQuery(new RangeHead(elements/* : T[] */.length/* : unknown */)/* : RangeHead */)/* : HeadedQuery */.map/* : (arg0 : (arg0 : T) => R) => Query<R> */((index : T) => /* elements[index] */)/* : Query<R> */;
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */class FunctionType/*  */ implements Type {
	arguments : List<Type>;
	returns : Type;
	constructor (arguments : List<Type>, returns : Type) {
		this/* : unknown */.arguments/* : unknown */ = arguments/* : unknown */;
		this/* : unknown */.returns/* : unknown */ = returns/* : unknown */;
	}
	public generate() : string {
		let joined = this/* : FunctionType */.arguments/* : List<Type> */()/* : unknown */.iterateWithIndices/* : unknown */()/* : unknown */.map/* : unknown */((pair) => "arg" + pair/* : unknown */.left/* : unknown */()/* : unknown */ + " : " + pair/* : unknown */.right/* : unknown */()/* : unknown */.generate/* : unknown */()/* : unknown */)/* : unknown */.collect/* : unknown */(new Joiner(", ")/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return "(" + joined/* : unknown */ + ") => " + this/* : FunctionType */.returns/* : unknown */.generate/* : unknown */()/* : unknown */;
	}
	public replace(mapping : Map<string, Type>) : Type {
		return new FunctionType(this/* : FunctionType */.arguments/* : List<Type> */.query/* : () => Query<Type> */()/* : Query<Type> */.map/* : (arg0 : (arg0 : Type) => R) => Query<R> */((type : Type) => type/* : Type */.replace/* : (arg0 : Map<string, Type>) => Type */(mapping/* : Map<string, Type> */)/* : Type */)/* : Query<R> */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */, this/* : FunctionType */.returns/* : Type */.replace/* : (arg0 : Map<string, Type>) => Type */(mapping/* : Map<string, Type> */)/* : Type */)/* : FunctionType */;
	}
	public findName() : string {
		return "";
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */class TupleType/*  */ implements Type {
	arguments : List<Type>;
	constructor (arguments : List<Type>) {
		this/* : unknown */.arguments/* : unknown */ = arguments/* : unknown */;
	}
	public generate() : string {
		let joinedArguments = this/* : TupleType */.arguments/* : List<Type> */.query/* : () => Query<Type> */()/* : Query<Type> */.map/* : (arg0 : (arg0 : Type) => R) => Query<R> */(Type/* : Type */.generate/* : unknown */)/* : Query<R> */.collect/* : unknown */(new Joiner(", ")/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return "[" + joinedArguments + "]";
	}
	public replace(mapping : Map<string, Type>) : Type {
		return this/* : TupleType */;
	}
	public findName() : string {
		return "";
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */class Template/*  */ implements FindableType {
	base : ObjectType;
	arguments : List<Type>;
	constructor (base : ObjectType, arguments : List<Type>) {
		this/* : unknown */.base/* : unknown */ = base/* : unknown */;
		this/* : unknown */.arguments/* : unknown */ = arguments/* : unknown */;
	}
	_FindableTypeVariant : FindableTypeVariant = FindableTypeVariant.Template/* : FindableTypeVariant */;
	public generate() : string {
		let joinedArguments = this/* : Template */.arguments/* : List<Type> */.query/* : () => Query<Type> */()/* : Query<Type> */.map/* : (arg0 : (arg0 : Type) => R) => Query<R> */(Type/* : Type */.generate/* : unknown */)/* : Query<R> */.collect/* : unknown */(new Joiner(", ")/* : Joiner */)/* : unknown */.map/* : unknown */((inner) => "<" + inner + ">")/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return this/* : Template */.base/* : ObjectType */.generate/* : () => string */()/* : string */ + joinedArguments/* : unknown */;
	}
	public find(name : string) : Option<Type> {
		return this/* : Template */.base/* : ObjectType */.find/* : (arg0 : string) => Option<Type> */(name/* : string */)/* : Option<Type> */.map/* : (arg0 : (arg0 : Type) => R) => Option<R> */((found : Type) => {
			let mapping = this/* : Template */.base/* : ObjectType */.typeParams/* : List<string> */()/* : unknown */.query/* : unknown */()/* : unknown */.zip/* : unknown */(this/* : Template */.arguments/* : List<Type> */.query/* : () => Query<Type> */()/* : Query<Type> */)/* : unknown */.collect/* : unknown */(new MapCollector()/* : MapCollector */)/* : unknown */;
			return found/* : Type */.replace/* : (arg0 : Map<string, Type>) => Type */(mapping/* : unknown */)/* : Type */;
		})/* : Option<R> */;
	}
	public findBase() : Option<BaseType> {
		return new Some(this/* : Template */.base/* : ObjectType */)/* : Some */;
	}
	public replace(mapping : Map<string, Type>) : Type {
		let collect = this/* : Template */.arguments/* : List<Type> */.query/* : () => Query<Type> */()/* : Query<Type> */.map/* : (arg0 : (arg0 : Type) => R) => Query<R> */((argument : Type) => argument/* : Type */.replace/* : (arg0 : Map<string, Type>) => Type */(mapping/* : Map<string, Type> */)/* : Type */)/* : Query<R> */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */;
		return new Template(this/* : Template */.base/* : ObjectType */, collect/* : unknown */)/* : Template */;
	}
	public findName() : string {
		return this/* : Template */.base/* : ObjectType */.findName/* : () => string */()/* : string */;
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */class Placeholder/*  */ implements Parameter, Value, FindableType, ClassSegment, FunctionSegment, BlockHeader, StatementValue, IncompleteClassSegment {
	input : string;
	constructor (input : string) {
		this/* : unknown */.input/* : unknown */ = input/* : unknown */;
	}
	_IncompleteClassSegmentVariant : IncompleteClassSegmentVariant = IncompleteClassSegmentVariant.Placeholder/* : IncompleteClassSegmentVariant */;
	_FindableTypeVariant : FindableTypeVariant = FindableTypeVariant.Placeholder/* : FindableTypeVariant */;
	_ValueVariant : ValueVariant = ValueVariant.Placeholder/* : ValueVariant */;
	_ParameterVariant : ParameterVariant = ParameterVariant.Placeholder/* : ParameterVariant */;
	public generate() : string {
		return generatePlaceholder/* : (arg0 : string) => string */(this/* : Placeholder */.input/* : string */)/* : string */;
	}
	public type() : Type {
		return Primitive/* : Primitive */.Unknown/* : unknown */;
	}
	public find(name : string) : Option<Type> {
		return new None()/* : None */;
	}
	public findBase() : Option<BaseType> {
		return new None()/* : None */;
	}
	public replace(mapping : Map<string, Type>) : Type {
		return this/* : Placeholder */;
	}
	public findName() : string {
		return "";
	}
	public maybeCreateDefinition() : Option<Definition> {
		return new None()/* : None */;
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */class StringValue/*  */ implements Value {
	value : string;
	constructor (value : string) {
		this/* : unknown */.value/* : unknown */ = value/* : unknown */;
	}
	_ValueVariant : ValueVariant = ValueVariant.StringValue/* : ValueVariant */;
	public generate() : string {
		return "\"" + this.value + "\"";
	}
	public type() : Type {
		return Primitive/* : Primitive */.Unknown/* : unknown */;
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */class DataAccess/*  */ implements Value {
	parent : Value;
	property : string;
	type : Type;
	constructor (parent : Value, property : string, type : Type) {
		this/* : unknown */.parent/* : unknown */ = parent/* : unknown */;
		this/* : unknown */.property/* : unknown */ = property/* : unknown */;
		this/* : unknown */.type/* : unknown */ = type/* : unknown */;
	}
	_ValueVariant : ValueVariant = ValueVariant.DataAccess/* : ValueVariant */;
	public generate() : string {
		return this/* : DataAccess */.parent/* : Value */.generate/* : () => string */()/* : string */ + "." + this/* : DataAccess */.property/* : string */ + createDebugString/* : (arg0 : Type) => string */(this/* : DataAccess */.type/* : () => Type */)/* : unknown */;
	}
	public type() : Type {
		return this/* : DataAccess */.type/* : () => Type */;
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */class ConstructionCaller/*  */ implements Caller {
	type : Type;
	constructor (type : Type) {
		this/* : unknown */.type/* : unknown */ = type/* : unknown */;
	}
	_CallerVariant : CallerVariant = CallerVariant.ConstructionCaller/* : CallerVariant */;
	public generate() : string {
		return "new " + this/* : ConstructionCaller */.type/* : unknown */.generate/* : unknown */()/* : unknown */;
	}
	public toFunction() : FunctionType {
		return new FunctionType(Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, this/* : ConstructionCaller */.type/* : Type */)/* : FunctionType */;
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */class Operator/*  */ {
	sourceRepresentation : string;
	targetRepresentation : string;
	constructor (sourceRepresentation : string, targetRepresentation : string) {
		this/* : unknown */.sourceRepresentation/* : unknown */ = sourceRepresentation/* : unknown */;
		this/* : unknown */.targetRepresentation/* : unknown */ = targetRepresentation/* : unknown */;
	}
	static ADD : Operator = new Operator("+", "+")/* : Operator */;
	static AND : Operator = new Operator("&&", "&&")/* : Operator */;
	static EQUALS : Operator = new Operator("==", "===")/* : Operator */;
	static Operator GREATER_THAN_OR_EQUALS = Operator() : /* new */;
	static Operator LESS_THAN = Operator() : /* new */;
	static OR : Operator = new Operator("||", "||")/* : Operator */;
	static SUBTRACT : Operator = new Operator("-", "-")/* : Operator */;
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */class Operation/*  */ implements Value {
	left : Value;
	operator : Operator;
	right : Value;
	constructor (left : Value, operator : Operator, right : Value) {
		this/* : unknown */.left/* : unknown */ = left/* : unknown */;
		this/* : unknown */.operator/* : unknown */ = operator/* : unknown */;
		this/* : unknown */.right/* : unknown */ = right/* : unknown */;
	}
	_ValueVariant : ValueVariant = ValueVariant.Operation/* : ValueVariant */;
	public generate() : string {
		return this/* : Operation */.left/* : Value */()/* : unknown */.generate/* : unknown */()/* : unknown */ + " " + this/* : Operation */.operator/* : Operator */.targetRepresentation/* : unknown */ + " " + this/* : Operation */.right/* : unknown */()/* : unknown */.generate/* : unknown */()/* : unknown */;
	}
	public type() : Type {
		return Primitive/* : Primitive */.Unknown/* : unknown */;
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */class Not/*  */ implements Value {
	value : Value;
	constructor (value : Value) {
		this/* : unknown */.value/* : unknown */ = value/* : unknown */;
	}
	_ValueVariant : ValueVariant = ValueVariant.Not/* : ValueVariant */;
	public generate() : string {
		return "!" + this/* : Not */.value/* : unknown */.generate/* : unknown */()/* : unknown */;
	}
	public type() : Type {
		return Primitive/* : Primitive */.Unknown/* : unknown */;
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */class BlockLambdaValue/*  */ implements LambdaValue {
	depth : number;
	statements : List<FunctionSegment>;
	constructor (depth : number, statements : List<FunctionSegment>) {
		this/* : unknown */.depth/* : unknown */ = depth/* : unknown */;
		this/* : unknown */.statements/* : unknown */ = statements/* : unknown */;
	}
	public generate() : string {
		return "{" + this.joinStatements() + createIndent(this.depth) + "}";
	}
	joinStatements() : string {
		return this/* : BlockLambdaValue */.statements/* : List<FunctionSegment> */.query/* : () => Query<FunctionSegment> */()/* : Query<FunctionSegment> */.map/* : (arg0 : (arg0 : FunctionSegment) => R) => Query<R> */(FunctionSegment/* : FunctionSegment */.generate/* : unknown */)/* : Query<R> */.collect/* : unknown */(Joiner/* : Joiner */.empty/* : () => Joiner */()/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */class Lambda/*  */ implements Value {
	parameters : List<Definition>;
	body : LambdaValue;
	constructor (parameters : List<Definition>, body : LambdaValue) {
		this/* : unknown */.parameters/* : unknown */ = parameters/* : unknown */;
		this/* : unknown */.body/* : unknown */ = body/* : unknown */;
	}
	_ValueVariant : ValueVariant = ValueVariant.Lambda/* : ValueVariant */;
	public generate() : string {
		let joined = this/* : Lambda */.parameters/* : List<Definition> */.query/* : () => Query<Definition> */()/* : Query<Definition> */.map/* : (arg0 : (arg0 : Definition) => R) => Query<R> */(Definition/* : Definition */.generate/* : unknown */)/* : Query<R> */.collect/* : unknown */(new Joiner(", ")/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return "(" + joined/* : unknown */ + ") => " + this/* : Lambda */.body/* : unknown */.generate/* : unknown */()/* : unknown */;
	}
	public type() : Type {
		return Primitive/* : Primitive */.Unknown/* : unknown */;
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */class Invokable/*  */ implements Value {
	caller : Caller;
	arguments : List<Value>;
	type : Type;
	constructor (caller : Caller, arguments : List<Value>, type : Type) {
		this/* : unknown */.caller/* : unknown */ = caller/* : unknown */;
		this/* : unknown */.arguments/* : unknown */ = arguments/* : unknown */;
		this/* : unknown */.type/* : unknown */ = type/* : unknown */;
	}
	_ValueVariant : ValueVariant = ValueVariant.Invokable/* : ValueVariant */;
	public generate() : string {
		let joined = this/* : Invokable */.arguments/* : List<Value> */.query/* : () => Query<Value> */()/* : Query<Value> */.map/* : (arg0 : (arg0 : Value) => R) => Query<R> */(Value/* : Value */.generate/* : unknown */)/* : Query<R> */.collect/* : unknown */(new Joiner(", ")/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return this/* : Invokable */.caller/* : Caller */.generate/* : () => string */()/* : string */ + "(" + joined/* : unknown */ + ")" + createDebugString/* : (arg0 : Type) => string */(this/* : Invokable */.type/* : Type */)/* : unknown */;
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */class IndexValue/*  */ implements Value {
	parent : Value;
	child : Value;
	constructor (parent : Value, child : Value) {
		this/* : unknown */.parent/* : unknown */ = parent/* : unknown */;
		this/* : unknown */.child/* : unknown */ = child/* : unknown */;
	}
	_ValueVariant : ValueVariant = ValueVariant.IndexValue/* : ValueVariant */;
	public generate() : string {
		return this/* : IndexValue */.parent/* : Value */.generate/* : () => string */()/* : string */ + "[" + this.child.generate() + "]";
	}
	public type() : Type {
		return Primitive/* : Primitive */.Unknown/* : unknown */;
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */class SymbolValue/*  */ implements Value {
	stripped : string;
	type : Type;
	constructor (stripped : string, type : Type) {
		this/* : unknown */.stripped/* : unknown */ = stripped/* : unknown */;
		this/* : unknown */.type/* : unknown */ = type/* : unknown */;
	}
	_ValueVariant : ValueVariant = ValueVariant.SymbolValue/* : ValueVariant */;
	public generate() : string {
		return this/* : SymbolValue */.stripped/* : string */ + createDebugString/* : (arg0 : Type) => string */(this/* : SymbolValue */.type/* : Type */)/* : unknown */;
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private static */class Maps/*  */ {
	public static empty<V, K>() : Map<K, V>;
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */class MapCollector<K, V>/*  */ implements Collector<[K, V], Map<K, V>> {
	public createInitial() : Map<K, V> {
		return Maps/* : Maps */.empty/* : () => Map<K, V> */()/* : Map<K, V> */;
	}
	public fold(current : Map<K, V>, element : [K, V]) : Map<K, V> {
		return current/* : Map<K, V> */.with/* : (arg0 : K, arg1 : V) => Map<K, V> */(element/* : [K, V] */[0/* : number */], element/* : [K, V] */[1/* : number */])/* : Map<K, V> */;
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private static */class ConstructorHeader/*  */ implements Header {
	public createDefinition(paramTypes : List<Type>) : Definition {
		return ImmutableDefinition/* : ImmutableDefinition */.createSimpleDefinition/* : (arg0 : string, arg1 : Type) => Definition */("new", Primitive/* : Primitive */.Unknown/* : unknown */)/* : Definition */;
	}
	public generateWithParams(joinedParameters : string) : string {
		return "constructor " + joinedParameters/* : string */;
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */class FunctionNode/*  */ implements ClassSegment {
	depth : number;
	header : Header;
	parameters : List<Definition>;
	maybeStatements : Option<List<FunctionSegment>>;
	constructor (depth : number, header : Header, parameters : List<Definition>, maybeStatements : Option<List<FunctionSegment>>) {
		this/* : unknown */.depth/* : unknown */ = depth/* : unknown */;
		this/* : unknown */.header/* : unknown */ = header/* : unknown */;
		this/* : unknown */.parameters/* : unknown */ = parameters/* : unknown */;
		this/* : unknown */.maybeStatements/* : unknown */ = maybeStatements/* : unknown */;
	}
	static joinStatements(statements : List<FunctionSegment>) : string {
		return statements/* : List<FunctionSegment> */.query/* : () => Query<FunctionSegment> */()/* : Query<FunctionSegment> */.map/* : (arg0 : (arg0 : FunctionSegment) => R) => Query<R> */(FunctionSegment/* : FunctionSegment */.generate/* : unknown */)/* : Query<R> */.collect/* : unknown */(Joiner/* : Joiner */.empty/* : () => Joiner */()/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
	}
	public generate() : string {
		let indent : string = createIndent/* : (arg0 : number) => string */(this/* : FunctionNode */.depth/* : number */)/* : string */;
		let generatedHeader : string = this/* : FunctionNode */.header/* : Header */.generateWithParams/* : (arg0 : string) => string */(joinValues/* : (arg0 : List<Definition>) => string */(this/* : FunctionNode */.parameters/* : List<Definition> */)/* : string */)/* : string */;
		let generatedStatements = this/* : FunctionNode */.maybeStatements/* : Option<List<FunctionSegment>> */.map/* : (arg0 : (arg0 : List<FunctionSegment>) => R) => Option<R> */(FunctionNode/* : FunctionNode */.joinStatements/* : unknown */)/* : Option<R> */.map/* : unknown */((inner) => " {" + inner + indent + "}")/* : unknown */.orElse/* : unknown */(";")/* : unknown */;
		return indent/* : string */ + generatedHeader/* : string */ + generatedStatements/* : unknown */;
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */class Block/*  */ implements FunctionSegment {
	depth : number;
	header : BlockHeader;
	statements : List<FunctionSegment>;
	constructor (depth : number, header : BlockHeader, statements : List<FunctionSegment>) {
		this/* : unknown */.depth/* : unknown */ = depth/* : unknown */;
		this/* : unknown */.header/* : unknown */ = header/* : unknown */;
		this/* : unknown */.statements/* : unknown */ = statements/* : unknown */;
	}
	public generate() : string {
		let indent : string = createIndent/* : (arg0 : number) => string */(this/* : Block */.depth/* : number */)/* : string */;
		let collect = this/* : Block */.statements/* : List<FunctionSegment> */.query/* : () => Query<FunctionSegment> */()/* : Query<FunctionSegment> */.map/* : (arg0 : (arg0 : FunctionSegment) => R) => Query<R> */(FunctionSegment/* : FunctionSegment */.generate/* : unknown */)/* : Query<R> */.collect/* : unknown */(Joiner/* : Joiner */.empty/* : () => Joiner */()/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return indent/* : string */ + this/* : Block */.header/* : BlockHeader */.generate/* : () => string */()/* : string */ + "{" + collect + indent + "}";
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */class Conditional/*  */ implements BlockHeader {
	prefix : string;
	value1 : Value;
	constructor (prefix : string, value1 : Value) {
		this/* : unknown */.prefix/* : unknown */ = prefix/* : unknown */;
		this/* : unknown */.value1/* : unknown */ = value1/* : unknown */;
	}
	public generate() : string {
		return this/* : Conditional */.prefix/* : string */ + " (" + this.value1.generate() + ")";
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private static */class Else/*  */ implements BlockHeader {
	public generate() : string {
		return "else ";
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */class Return/*  */ implements StatementValue {
	value : Value;
	constructor (value : Value) {
		this/* : unknown */.value/* : unknown */ = value/* : unknown */;
	}
	public generate() : string {
		return "return " + this/* : Return */.value/* : unknown */.generate/* : unknown */()/* : unknown */;
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */class Initialization/*  */ implements StatementValue {
	definition : Definition;
	source : Value;
	constructor (definition : Definition, source : Value) {
		this/* : unknown */.definition/* : unknown */ = definition/* : unknown */;
		this/* : unknown */.source/* : unknown */ = source/* : unknown */;
	}
	public generate() : string {
		return "let " + this/* : Initialization */.definition/* : Definition */.generate/* : () => string */()/* : string */ + " = " + this/* : Initialization */.source/* : unknown */.generate/* : unknown */()/* : unknown */;
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */class FieldInitialization/*  */ implements StatementValue {
	definition : Definition;
	source : Value;
	constructor (definition : Definition, source : Value) {
		this/* : unknown */.definition/* : unknown */ = definition/* : unknown */;
		this/* : unknown */.source/* : unknown */ = source/* : unknown */;
	}
	public generate() : string {
		return this/* : FieldInitialization */.definition/* : Definition */.generate/* : () => string */()/* : string */ + " = " + this/* : FieldInitialization */.source/* : unknown */.generate/* : unknown */()/* : unknown */;
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */class Assignment/*  */ implements StatementValue {
	destination : Value;
	source : Value;
	constructor (destination : Value, source : Value) {
		this/* : unknown */.destination/* : unknown */ = destination/* : unknown */;
		this/* : unknown */.source/* : unknown */ = source/* : unknown */;
	}
	public generate() : string {
		return this/* : Assignment */.destination/* : Value */.generate/* : () => string */()/* : string */ + " = " + this/* : Assignment */.source/* : unknown */.generate/* : unknown */()/* : unknown */;
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */class Statement/*  */ implements FunctionSegment, ClassSegment {
	depth : number;
	value : StatementValue;
	constructor (depth : number, value : StatementValue) {
		this/* : unknown */.depth/* : unknown */ = depth/* : unknown */;
		this/* : unknown */.value/* : unknown */ = value/* : unknown */;
	}
	public generate() : string {
		return createIndent/* : (arg0 : number) => string */(this/* : Statement */.depth/* : number */)/* : string */ + this/* : Statement */.value/* : StatementValue */.generate/* : () => string */()/* : string */ + ";";
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */class MethodPrototype/*  */ implements IncompleteClassSegment {
	depth : number;
	header : Header;
	parameters : List<Definition>;
	content : string;
	constructor (depth : number, header : Header, parameters : List<Definition>, content : string) {
		this/* : unknown */.depth/* : unknown */ = depth/* : unknown */;
		this/* : unknown */.header/* : unknown */ = header/* : unknown */;
		this/* : unknown */.parameters/* : unknown */ = parameters/* : unknown */;
		this/* : unknown */.content/* : unknown */ = content/* : unknown */;
	}
	_IncompleteClassSegmentVariant : IncompleteClassSegmentVariant = IncompleteClassSegmentVariant.MethodPrototype/* : IncompleteClassSegmentVariant */;
	createDefinition() : Definition {
		return this/* : MethodPrototype */.header/* : Header */.createDefinition/* : (arg0 : List<Type>) => Definition */(this/* : MethodPrototype */.findParamTypes/* : () => List<Type> */()/* : List<Type> */)/* : Definition */;
	}
	findParamTypes() : List<Type> {
		return this/* : MethodPrototype */.parameters/* : List<Definition> */()/* : unknown */.query/* : unknown */()/* : unknown */.map/* : unknown */(Definition/* : Definition */.findType/* : unknown */)/* : unknown */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */;
	}
	public maybeCreateDefinition() : Option<Definition> {
		return new Some(this/* : MethodPrototype */.header/* : Header */.createDefinition/* : (arg0 : List<Type>) => Definition */(this/* : MethodPrototype */.findParamTypes/* : () => List<Type> */()/* : List<Type> */)/* : Definition */)/* : Some */;
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */class IncompleteClassSegmentWrapper/*  */ implements IncompleteClassSegment {
	segment : ClassSegment;
	constructor (segment : ClassSegment) {
		this/* : unknown */.segment/* : unknown */ = segment/* : unknown */;
	}
	_IncompleteClassSegmentVariant : IncompleteClassSegmentVariant = IncompleteClassSegmentVariant.IncompleteClassSegmentWrapper/* : IncompleteClassSegmentVariant */;
	public maybeCreateDefinition() : Option<Definition> {
		return new None()/* : None */;
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */class ClassDefinition/*  */ implements IncompleteClassSegment {
	depth : number;
	definition : Definition;
	constructor (depth : number, definition : Definition) {
		this/* : unknown */.depth/* : unknown */ = depth/* : unknown */;
		this/* : unknown */.definition/* : unknown */ = definition/* : unknown */;
	}
	_IncompleteClassSegmentVariant : IncompleteClassSegmentVariant = IncompleteClassSegmentVariant.ClassDefinition/* : IncompleteClassSegmentVariant */;
	public maybeCreateDefinition() : Option<Definition> {
		return new Some(this/* : ClassDefinition */.definition/* : Definition */)/* : Some */;
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */class ClassInitialization/*  */ implements IncompleteClassSegment {
	depth : number;
	definition : Definition;
	value : Value;
	constructor (depth : number, definition : Definition, value : Value) {
		this/* : unknown */.depth/* : unknown */ = depth/* : unknown */;
		this/* : unknown */.definition/* : unknown */ = definition/* : unknown */;
		this/* : unknown */.value/* : unknown */ = value/* : unknown */;
	}
	_IncompleteClassSegmentVariant : IncompleteClassSegmentVariant = IncompleteClassSegmentVariant.ClassInitialization/* : IncompleteClassSegmentVariant */;
	public maybeCreateDefinition() : Option<Definition> {
		return new Some(this/* : ClassInitialization */.definition/* : Definition */)/* : Some */;
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */class StructurePrototype/*  */ implements IncompleteClassSegment {
	targetInfix : string;
	beforeInfix : string;
	name : string;
	typeParams : List<string>;
	parameters : List<Definition>;
	after : string;
	segments : List<IncompleteClassSegment>;
	variants : List<string>;
	interfaces : List<Type>;
	superTypes : List<Type>;
	constructor (targetInfix : string, beforeInfix : string, name : string, typeParams : List<string>, parameters : List<Definition>, after : string, segments : List<IncompleteClassSegment>, variants : List<string>, interfaces : List<Type>, superTypes : List<Type>) {
		this/* : unknown */.targetInfix/* : unknown */ = targetInfix/* : unknown */;
		this/* : unknown */.beforeInfix/* : unknown */ = beforeInfix/* : unknown */;
		this/* : unknown */.name/* : unknown */ = name/* : unknown */;
		this/* : unknown */.typeParams/* : unknown */ = typeParams/* : unknown */;
		this/* : unknown */.parameters/* : unknown */ = parameters/* : unknown */;
		this/* : unknown */.after/* : unknown */ = after/* : unknown */;
		this/* : unknown */.segments/* : unknown */ = segments/* : unknown */;
		this/* : unknown */.variants/* : unknown */ = variants/* : unknown */;
		this/* : unknown */.interfaces/* : unknown */ = interfaces/* : unknown */;
		this/* : unknown */.superTypes/* : unknown */ = superTypes/* : unknown */;
	}
	_IncompleteClassSegmentVariant : IncompleteClassSegmentVariant = IncompleteClassSegmentVariant.StructurePrototype/* : IncompleteClassSegmentVariant */;
	createObjectType() : ObjectType {
		let definitionFromSegments = this/* : StructurePrototype */.segments/* : List<IncompleteClassSegment> */.query/* : () => Query<IncompleteClassSegment> */()/* : Query<IncompleteClassSegment> */.map/* : (arg0 : (arg0 : IncompleteClassSegment) => R) => Query<R> */(IncompleteClassSegment/* : IncompleteClassSegment */.maybeCreateDefinition/* : unknown */)/* : Query<R> */.flatMap/* : (arg0 : (arg0 : T) => Option<R>) => Option<R> */(Queries/* : Queries */.fromOption/* : unknown */)/* : Option<R> */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */;
		return new ObjectType(this/* : StructurePrototype */.name/* : string */, this/* : StructurePrototype */.typeParams/* : List<string> */, definitionFromSegments/* : unknown */.addAllLast/* : unknown */(this/* : StructurePrototype */.parameters/* : List<Definition> */)/* : unknown */, this/* : StructurePrototype */.variants/* : List<string> */)/* : ObjectType */;
	}
	public maybeCreateDefinition() : Option<Definition> {
		return new None()/* : None */;
	}
	joinInterfaces() : string {
		return this/* : StructurePrototype */.interfaces/* : List<Type> */.query/* : () => Query<Type> */()/* : Query<Type> */.map/* : (arg0 : (arg0 : Type) => R) => Query<R> */(Type/* : Type */.generate/* : unknown */)/* : Query<R> */.collect/* : unknown */(new Joiner(", ")/* : Joiner */)/* : unknown */.map/* : unknown */((inner) => " implements " + inner/* : unknown */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
	}
	joinTypeParams() : string {
		return this/* : StructurePrototype */.typeParams/* : List<string> */()/* : unknown */.query/* : unknown */()/* : unknown */.collect/* : unknown */(new Joiner(", ")/* : Joiner */)/* : unknown */.map/* : unknown */((inner) => "<" + inner + ">")/* : unknown */.orElse/* : unknown */("")/* : unknown */;
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */class Cast/*  */ implements Value {
	value : Value;
	type : Type;
	constructor (value : Value, type : Type) {
		this/* : unknown */.value/* : unknown */ = value/* : unknown */;
		this/* : unknown */.type/* : unknown */ = type/* : unknown */;
	}
	_ValueVariant : ValueVariant = ValueVariant.Cast/* : ValueVariant */;
	public generate() : string {
		return this/* : Cast */.value/* : Value */.generate/* : () => string */()/* : string */ + " as " + this/* : Cast */.type/* : unknown */.generate/* : unknown */()/* : unknown */;
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */class Ok<T, X>/*  */ implements Result<T, X> {
	value : T;
	constructor (value : T) {
		this/* : unknown */.value/* : unknown */ = value/* : unknown */;
	}
	_ResultVariant : ResultVariant = ResultVariant.Ok/* : ResultVariant */;
	public mapValue<R>(mapper : (arg0 : T) => R) : Result<R, X> {
		return new Ok(mapper/* : (arg0 : T) => R */(this/* : Ok */.value/* : T */)/* : R */)/* : Ok */;
	}
	public match<R>(whenOk : (arg0 : T) => R, whenErr : (arg0 : X) => R) : R {
		return whenOk/* : (arg0 : T) => R */(this/* : Ok */.value/* : T */)/* : R */;
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */class Err<T, X>/*  */ implements Result<T, X> {
	error : X;
	constructor (error : X) {
		this/* : unknown */.error/* : unknown */ = error/* : unknown */;
	}
	_ResultVariant : ResultVariant = ResultVariant.Err/* : ResultVariant */;
	public mapValue<R>(mapper : (arg0 : T) => R) : Result<R, X> {
		return new Err(this/* : Err */.error/* : X */)/* : Err */;
	}
	public match<R>(whenOk : (arg0 : T) => R, whenErr : (arg0 : X) => R) : R {
		return whenErr/* : (arg0 : X) => R */(this/* : Err */.error/* : X */)/* : R */;
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */class JVMIOError/*  */ implements IOError {
	error : /* IOException */;
	constructor (error : /* IOException */) {
		this/* : unknown */.error/* : unknown */ = error/* : unknown */;
	}
	public display() : string {
		let writer : /* StringWriter */ = new /* StringWriter */()/* : content-start StringWriter content-end */;
		/* this.error.printStackTrace(new PrintWriter(writer)) */;
		return writer/* : content-start StringWriter content-end */.toString/* : unknown */()/* : unknown */;
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */class TupleNode/*  */ implements Value {
	values : List<Value>;
	constructor (values : List<Value>) {
		this/* : unknown */.values/* : unknown */ = values/* : unknown */;
	}
	_ValueVariant : ValueVariant = ValueVariant.TupleNode/* : ValueVariant */;
	public generate() : string {
		let joined = this/* : TupleNode */.values/* : List<Value> */.query/* : () => Query<Value> */()/* : Query<Value> */.map/* : (arg0 : (arg0 : Value) => R) => Query<R> */(Value/* : Value */.generate/* : unknown */)/* : Query<R> */.collect/* : unknown */(new Joiner(", ")/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return "[" + joined + "]";
	}
	public type() : Type {
		return new TupleType(this/* : TupleNode */.values/* : List<Value> */.query/* : () => Query<Value> */()/* : Query<Value> */.map/* : (arg0 : (arg0 : Value) => R) => Query<R> */(Value/* : Value */.type/* : unknown */)/* : Query<R> */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */)/* : TupleType */;
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */class MapHead<T, R>/*  */ implements Head<R> {
	head : Head<T>;
	mapper : (arg0 : T) => R;
	constructor (head : Head<T>, mapper : (arg0 : T) => R) {
		this/* : unknown */.head/* : unknown */ = head/* : unknown */;
		this/* : unknown */.mapper/* : unknown */ = mapper/* : unknown */;
	}
	public next() : Option<R> {
		return this/* : MapHead */.head/* : Head<T> */.next/* : () => Option<T> */()/* : Option<T> */.map/* : (arg0 : (arg0 : T) => R) => Option<R> */(this/* : MapHead */.mapper/* : (arg0 : T) => R */)/* : Option<R> */;
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */class ZipHead<T, R>/*  */ implements Head<[T, R]> {
	head : Head<T>;
	other : Query<R>;
	constructor (head : Head<T>, other : Query<R>) {
		this/* : unknown */.head/* : unknown */ = head/* : unknown */;
		this/* : unknown */.other/* : unknown */ = other/* : unknown */;
	}
	public next() : Option<[T, R]> {
		return this/* : ZipHead */.head/* : Head<T> */.next/* : () => Option<T> */()/* : Option<T> */.and/* : (arg0 : () => Option<R>) => Option<[T, R]> */(this/* : ZipHead */.other/* : Query<R> */.next/* : unknown */)/* : Option<[T, R]> */;
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */class EnumValue/*  */ {
	value : string;
	values : List<Value>;
	constructor (value : string, values : List<Value>) {
		this/* : unknown */.value/* : unknown */ = value/* : unknown */;
		this/* : unknown */.values/* : unknown */ = values/* : unknown */;
	}
	public generate() : string {
		let s = this/* : EnumValue */.values/* : List<Value> */.query/* : () => Query<Value> */()/* : Query<Value> */.map/* : (arg0 : (arg0 : Value) => R) => Query<R> */(Value/* : Value */.generate/* : unknown */)/* : Query<R> */.collect/* : unknown */(new Joiner(", ")/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return this/* : EnumValue */.value/* : string */ + "(" + s + ")";
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */class EnumValues/*  */ implements IncompleteClassSegment, ClassSegment {
	values : List<EnumValue>;
	constructor (values : List<EnumValue>) {
		this/* : unknown */.values/* : unknown */ = values/* : unknown */;
	}
	_IncompleteClassSegmentVariant : IncompleteClassSegmentVariant = IncompleteClassSegmentVariant.EnumValues/* : IncompleteClassSegmentVariant */;
	public generate() : string {
		return this/* : EnumValues */.values/* : List<EnumValue> */.query/* : () => Query<EnumValue> */()/* : Query<EnumValue> */.map/* : (arg0 : (arg0 : EnumValue) => R) => Query<R> */(EnumValue/* : EnumValue */.generate/* : unknown */)/* : Query<R> */.collect/* : unknown */(new Joiner(", ")/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
	}
	public maybeCreateDefinition() : Option<Definition> {
		return new None()/* : None */;
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* public static */class Strings/*  */ {
	static length(infix : string) : number;
	static isBlank(input : string) : boolean {
		return input/* : string */.isBlank/* : unknown */()/* : unknown */;
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */class Primitive/*  */ implements Type {
	static Int : Primitive = new Primitive("number")/* : Primitive */;
	static String : Primitive = new Primitive("string")/* : Primitive */;
	static Boolean : Primitive = new Primitive("boolean")/* : Primitive */;
	static Unknown : Primitive = new Primitive("unknown")/* : Primitive */;
	static Void : Primitive = new Primitive("void")/* : Primitive */;
	readonly value : string;
	constructor (value : string) {
		this/* : Primitive */.value/* : string */ = value/* : string */;
	}
	public generate() : string {
		return this/* : Primitive */.value/* : string */;
	}
	public replace(mapping : Map<string, Type>) : Type {
		return this/* : Primitive */;
	}
	public findName() : string {
		return this/* : Primitive */.name/* : unknown */()/* : unknown */;
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* private */class BooleanValue/*  */ implements Value {
	_ValueVariant : ValueVariant = ValueVariant.BooleanValue/* : ValueVariant */;
	static True : BooleanValue = new BooleanValue("true")/* : BooleanValue */;
	static False : BooleanValue = new BooleanValue("false")/* : BooleanValue */;
	readonly value : string;
	constructor (value : string) {
		this/* : BooleanValue */.value/* : string */ = value/* : string */;
	}
	public generate() : string {
		return this/* : BooleanValue */.value/* : string */;
	}
	public type() : Type {
		return Primitive/* : Primitive */.Boolean/* : unknown */;
	}
}
/* [Actual,
	Argument,
	ArrayType,
	Assignment,
	BaseType,
	Block,
	BlockHeader,
	BlockLambdaValue,
	BooleanValue,
	Caller,
	Cast,
	ClassDefinition,
	ClassInitialization,
	ClassSegment,
	Collector,
	CompileState,
	Conditional,
	ConstructionCaller,
	ConstructorHeader,
	DataAccess,
	Definition,
	DivideState,
	Else,
	EmptyHead,
	EnumValue,
	EnumValues,
	Err,
	FieldInitialization,
	FindableType,
	FlatMapHead,
	FunctionNode,
	FunctionSegment,
	FunctionType,
	Head,
	HeadedQuery,
	Header,
	IOError,
	ImmutableDefinition,
	IncompleteClassSegment,
	IncompleteClassSegmentWrapper,
	IndexValue,
	Initialization,
	Invokable,
	JVMIOError,
	Joiner,
	Lambda,
	LambdaValue,
	List,
	ListCollector,
	Lists,
	Main,
	Map,
	MapCollector,
	MapHead,
	Maps,
	MethodPrototype,
	None,
	Not,
	ObjectType,
	Ok,
	Operation,
	Operator,
	Option,
	Parameter,
	Path,
	Placeholder,
	Primitive,
	Queries,
	Query,
	RangeHead,
	Result,
	Return,
	SingleHead,
	Some,
	Statement,
	StatementValue,
	StringValue,
	Strings,
	StructurePrototype,
	SymbolValue,
	Template,
	Tuple2,
	TupleNode,
	TupleType,
	Type,
	TypeParam,
	Value,
	Whitespace,
	ZipHead
] */
/* public */class Main/*  */ {
	JVMPath() : /* record */;
	static readonly isDebugEnabled : boolean = true;
	static generatePlaceholder(input : string) : string {
		let replaced = input/* : string */.replace/* : unknown */("/*", "content-start")/* : unknown */.replace/* : unknown */("*/", "content-end")/* : unknown */;
		return "/* " + replaced + " */";
	}
	static joinValues(retainParameters : List<Definition>) : string {
		let inner = retainParameters/* : List<Definition> */.query/* : () => Query<Definition> */()/* : Query<Definition> */.map/* : (arg0 : (arg0 : Definition) => R) => Query<R> */(Definition/* : Definition */.generate/* : unknown */)/* : Query<R> */.collect/* : unknown */(new Joiner(", ")/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return "(" + inner + ")";
	}
	static createIndent(depth : number) : string {
		return "\n" + "\t".repeat/* : unknown */(depth/* : number */)/* : unknown */;
	}
	static createDebugString(type : Type) : string {
		if (!Main/* : Main */.isDebugEnabled/* : unknown */){
			return "";
		}
		return generatePlaceholder/* : (arg0 : string) => string */(": " + type/* : Type */.generate/* : unknown */()/* : unknown */)/* : string */;
	}
	static retainFindableType(type : Type) : Option<FindableType> {
		if (type/* : Type */._TypeVariant/* : unknown */ === TypeVariant.FindableType/* : unknown */){
			let findableType : FindableType = type/* : Type */ as FindableType;
			return new Some(findableType/* : FindableType */)/* : Some */;
		}
		return new None()/* : None */;
	}
	static isSymbol(input : string) : boolean {
		/* for (var i = 0; i < Strings.length(input); i++) */{
			let c = input/* : string */.charAt/* : unknown */(/* i */)/* : unknown */;
			if (/* Character */.isLetter/* : unknown */(c/* : unknown */)/* : unknown */ || /*  */(/* i != 0  */ && /* Character */.isDigit/* : unknown */(c/* : unknown */)/* : unknown */)/* : unknown */){
				/* continue */;
			}
			return false;
		}
		return true;
	}
	static parseWhitespace(input : string, state : CompileState) : Option<[CompileState, Whitespace]> {
		if (Strings/* : Strings */.isBlank/* : (arg0 : string) => boolean */(input/* : string */)/* : boolean */){
			return new Some([state/* : CompileState */, new Whitespace()/* : Whitespace */])/* : Some */;
		}
		return new None()/* : None */;
	}
	public main() : void {
		let parent : Path = this/* : Main */.findRoot/* : () => Path */()/* : Path */;
		let source : Path = parent/* : Path */.resolve/* : (arg0 : string) => Path */("Main.java")/* : Path */;
		let target : Path = parent/* : Path */.resolve/* : (arg0 : string) => Path */("main.ts")/* : Path */;
		/* source.readString()
                .mapValue(this::compile)
                .match(target::writeString, Some::new)
                .or(this::executeTSC)
                .ifPresent(error -> System.err.println(error.display())) */;
	}
	findRoot() : Path;
	executeTSC() : Option<IOError>;
	compile(input : string) : string {
		let state : CompileState = CompileState/* : CompileState */.createInitial/* : () => CompileState */()/* : CompileState */;
		let parsed : [CompileState, List<T>] = this/* : Main */.parseStatements/* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => [CompileState, T]) => [CompileState, List<T>] */(state/* : CompileState */, input/* : string */, this/* : Main */.compileRootSegment/* : unknown */)/* : [CompileState, List<T>] */;
		let joined = parsed/* : [CompileState, List<T>] */[0/* : number */].structures/* : unknown */.query/* : unknown */()/* : unknown */.collect/* : unknown */(Joiner/* : Joiner */.empty/* : () => Joiner */()/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return joined/* : unknown */ + this/* : Main */.generateStatements/* : unknown */(parsed/* : [CompileState, List<T>] */[1/* : number */])/* : unknown */;
	}
	generateStatements(statements : List<string>) : string {
		return this/* : Main */.generateAll/* : (arg0 : (arg0 : string, arg1 : string) => string, arg1 : List<string>) => string */(this/* : Main */.mergeStatements/* : unknown */, statements/* : List<string> */)/* : string */;
	}
	parseStatements<T>(state : CompileState, input : string, mapper : (arg0 : CompileState, arg1 : string) => [CompileState, T]) : [CompileState, List<T>] {
		return this/* : Main */.parseAllWithIndices/* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : DivideState, arg1 : string) => DivideState, arg3 : (arg0 : CompileState, arg1 : [number, string]) => Option<[CompileState, T]>) => Option<[CompileState, List<T>]> */(state/* : CompileState */, input/* : string */, this/* : Main */.foldStatementChar/* : unknown */, (state3, tuple) => new Some(mapper/* : (arg0 : CompileState, arg1 : string) => [CompileState, T] */(state3/* : unknown */, tuple/* : unknown */.right/* : unknown */()/* : unknown */)/* : [CompileState, T] */)/* : Some */)/* : Option<[CompileState, List<T>]> */.orElseGet/* : (arg0 : () => [CompileState, List<T>]) => [CompileState, List<T>] */(() => [state/* : CompileState */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */])/* : [CompileState, List<T>] */;
	}
	generateAll(merger : (arg0 : string, arg1 : string) => string, elements : List<string>) : string {
		return elements/* : List<string> */.query/* : () => Query<string> */()/* : Query<string> */.fold/* : (arg0 : R, arg1 : (arg0 : R, arg1 : string) => R) => R */("", merger/* : (arg0 : string, arg1 : string) => string */)/* : R */;
	}
	parseAllWithIndices<T>(state : CompileState, input : string, folder : (arg0 : DivideState, arg1 : string) => DivideState, mapper : (arg0 : CompileState, arg1 : [number, string]) => Option<[CompileState, T]>) : Option<[CompileState, List<T>]> {
		let stringList : List<string> = this/* : Main */.divideAll/* : (arg0 : string, arg1 : (arg0 : DivideState, arg1 : string) => DivideState) => List<string> */(input/* : string */, folder/* : (arg0 : DivideState, arg1 : string) => DivideState */)/* : List<string> */;
		return this/* : Main */.mapUsingState/* : (arg0 : CompileState, arg1 : List<T>, arg2 : (arg0 : CompileState, arg1 : [number, T]) => Option<[CompileState, R]>) => Option<[CompileState, List<R>]> */(state/* : CompileState */, stringList/* : List<string> */, mapper/* : (arg0 : CompileState, arg1 : [number, string]) => Option<[CompileState, T]> */)/* : Option<[CompileState, List<R>]> */;
	}
	mapUsingState<T, R>(state : CompileState, elements : List<T>, mapper : (arg0 : CompileState, arg1 : [number, T]) => Option<[CompileState, R]>) : Option<[CompileState, List<R>]> {
		let initial : Option<[CompileState, List<R>]> = new Some([state/* : CompileState */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */])/* : Some */;
		return elements/* : List<T> */.iterateWithIndices/* : () => Query<[number, T]> */()/* : Query<[number, T]> */.fold/* : (arg0 : R, arg1 : (arg0 : R, arg1 : [number, T]) => R) => R */(initial/* : Option<[CompileState, List<R>]> */, (tuple, element) => {
			return tuple/* : unknown */.flatMap/* : unknown */((inner) => {
				let state1 = inner/* : unknown */.left/* : unknown */()/* : unknown */;
				let right = inner/* : unknown */.right/* : unknown */()/* : unknown */;
				return mapper/* : (arg0 : CompileState, arg1 : [number, T]) => Option<[CompileState, R]> */(state1/* : unknown */, element/* : unknown */)/* : Option<[CompileState, R]> */.map/* : (arg0 : (arg0 : [CompileState, R]) => R) => Option<R> */((applied : [CompileState, R]) => {
					return [applied/* : [CompileState, R] */[0/* : number */], right/* : unknown */.addLast/* : unknown */(applied/* : [CompileState, R] */[1/* : number */])/* : unknown */];
				})/* : Option<R> */;
			})/* : unknown */;
		})/* : R */;
	}
	mergeStatements(cache : string, statement : string) : string {
		return cache/* : string */ + statement/* : string */;
	}
	divideAll(input : string, folder : (arg0 : DivideState, arg1 : string) => DivideState) : List<string> {
		let current : DivideState = DivideState/* : DivideState */.createInitial/* : (arg0 : string) => DivideState */(input/* : string */)/* : DivideState */;
		while (true){
			let maybePopped = current/* : DivideState */.pop/* : unknown */()/* : unknown */.map/* : unknown */((tuple) => {
				return this/* : Main */.foldSingleQuotes/* : (arg0 : [string, DivideState]) => Option<DivideState> */(tuple/* : unknown */)/* : Option<DivideState> */.or/* : (arg0 : () => Option<DivideState>) => Option<DivideState> */(() => this/* : Main */.foldDoubleQuotes/* : (arg0 : [string, DivideState]) => Option<DivideState> */(tuple/* : unknown */)/* : Option<DivideState> */)/* : Option<DivideState> */.orElseGet/* : (arg0 : () => T) => T */(() => folder/* : (arg0 : DivideState, arg1 : string) => DivideState */(tuple/* : unknown */.right/* : unknown */()/* : unknown */, tuple/* : unknown */.left/* : unknown */()/* : unknown */)/* : DivideState */)/* : T */;
			})/* : unknown */;
			if (maybePopped/* : unknown */.isPresent/* : unknown */()/* : unknown */){
				current/* : DivideState */ = maybePopped/* : unknown */.orElse/* : unknown */(current/* : DivideState */)/* : unknown */;
			}
			else {
				/* break */;
			}
		}
		return current/* : DivideState */.advance/* : unknown */()/* : unknown */.segments/* : unknown */;
	}
	foldDoubleQuotes(tuple : [string, DivideState]) : Option<DivideState> {
		if (tuple/* : [string, DivideState] */[0/* : number */] === "\""){
			let current = tuple/* : [string, DivideState] */[1/* : number */].append/* : unknown */(tuple/* : [string, DivideState] */[0/* : number */])/* : unknown */;
			while (true){
				let maybePopped = current/* : unknown */.popAndAppendToTuple/* : unknown */()/* : unknown */;
				if (maybePopped/* : unknown */.isEmpty/* : unknown */()/* : unknown */){
					/* break */;
				}
				let popped = maybePopped/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */;
				current/* : unknown */ = popped/* : unknown */.right/* : unknown */()/* : unknown */;
				if (popped/* : unknown */.left/* : unknown */()/* : unknown */ === "\\"){
					current/* : unknown */ = current/* : unknown */.popAndAppendToOption/* : unknown */()/* : unknown */.orElse/* : unknown */(current/* : unknown */)/* : unknown */;
				}
				if (popped/* : unknown */.left/* : unknown */()/* : unknown */ === "\""){
					/* break */;
				}
			}
			return new Some(current/* : unknown */)/* : Some */;
		}
		return new None()/* : None */;
	}
	foldSingleQuotes(tuple : [string, DivideState]) : Option<DivideState> {
		if (/* tuple.left() != '\'' */){
			return new None()/* : None */;
		}
		let appended = tuple/* : [string, DivideState] */[1/* : number */].append/* : unknown */(tuple/* : [string, DivideState] */[0/* : number */])/* : unknown */;
		return appended/* : unknown */.popAndAppendToTuple/* : unknown */()/* : unknown */.map/* : unknown */(this/* : Main */.foldEscaped/* : unknown */)/* : unknown */.flatMap/* : unknown */(DivideState/* : DivideState */.popAndAppendToOption/* : unknown */)/* : unknown */;
	}
	foldEscaped(escaped : [string, DivideState]) : DivideState {
		if (escaped/* : [string, DivideState] */[0/* : number */] === "\\"){
			return escaped/* : [string, DivideState] */[1/* : number */].popAndAppendToOption/* : unknown */()/* : unknown */.orElse/* : unknown */(escaped/* : [string, DivideState] */[1/* : number */])/* : unknown */;
		}
		return escaped/* : [string, DivideState] */[1/* : number */];
	}
	foldStatementChar(state : DivideState, c : string) : DivideState {
		let append : DivideState = state/* : DivideState */.append/* : (arg0 : string) => DivideState */(c/* : string */)/* : DivideState */;
		if (c/* : string */ === ";" && append/* : DivideState */.isLevel/* : unknown */()/* : unknown */){
			return append/* : DivideState */.advance/* : () => DivideState */()/* : DivideState */;
		}
		if (c/* : string */ === "}" && append/* : DivideState */.isShallow/* : unknown */()/* : unknown */){
			return append/* : DivideState */.advance/* : () => DivideState */()/* : DivideState */.exit/* : unknown */()/* : unknown */;
		}
		if (c/* : string */ === "{" || c/* : string */ === "("){
			return append/* : DivideState */.enter/* : unknown */()/* : unknown */;
		}
		if (c/* : string */ === "}" || c/* : string */ === ")"){
			return append/* : DivideState */.exit/* : unknown */()/* : unknown */;
		}
		return append/* : DivideState */;
	}
	compileRootSegment(state : CompileState, input : string) : [CompileState, string] {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */.startsWith/* : unknown */("package ")/* : unknown */ || stripped/* : unknown */.startsWith/* : unknown */("import ")/* : unknown */){
			return [state/* : CompileState */, ""];
		}
		return this/* : Main */.parseClass/* : (arg0 : string, arg1 : CompileState) => Option<[CompileState, IncompleteClassSegment]> */(stripped/* : unknown */, state/* : CompileState */)/* : Option<[CompileState, IncompleteClassSegment]> */.flatMap/* : (arg0 : (arg0 : [CompileState, IncompleteClassSegment]) => Option<R>) => Option<R> */((tuple : [CompileState, IncompleteClassSegment]) => this/* : Main */.completeClassSegment/* : (arg0 : CompileState, arg1 : IncompleteClassSegment) => Option<[CompileState, ClassSegment]> */(tuple/* : [CompileState, IncompleteClassSegment] */[0/* : number */], tuple/* : [CompileState, IncompleteClassSegment] */[1/* : number */])/* : Option<[CompileState, ClassSegment]> */)/* : Option<R> */.map/* : (arg0 : (arg0 : T) => R) => Option<R> */((tuple0 : T) => [tuple0/* : T */.left/* : unknown */()/* : unknown */, tuple0/* : T */.right/* : unknown */()/* : unknown */.generate/* : unknown */()/* : unknown */])/* : Option<R> */.orElseGet/* : unknown */(() => [state/* : CompileState */, generatePlaceholder/* : (arg0 : string) => string */(stripped/* : unknown */)/* : string */])/* : unknown */;
	}
	parseClass(stripped : string, state : CompileState) : Option<[CompileState, IncompleteClassSegment]> {
		return this/* : Main */.parseStructure/* : (arg0 : string, arg1 : string, arg2 : string, arg3 : CompileState) => Option<[CompileState, IncompleteClassSegment]> */(stripped/* : string */, "class ", "class ", state/* : CompileState */)/* : Option<[CompileState, IncompleteClassSegment]> */;
	}
	parseStructure(stripped : string, sourceInfix : string, targetInfix : string, state : CompileState) : Option<[CompileState, IncompleteClassSegment]> {
		return this/* : Main */.first/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(stripped/* : string */, sourceInfix/* : string */, (beforeInfix, right) => {
			return this/* : Main */.first/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(right/* : unknown */, "{", (beforeContent, withEnd) => {
				return this/* : Main */.suffix/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(withEnd/* : unknown */.strip/* : unknown */()/* : unknown */, "}", (content1 : string) => {
					return this/* : Main */.last/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(beforeInfix/* : unknown */.strip/* : unknown */()/* : unknown */, "\n", (annotationsString, s2) => {
						let annotations : List<string> = this/* : Main */.parseAnnotations/* : (arg0 : string) => List<string> */(annotationsString/* : unknown */)/* : List<string> */;
						return this/* : Main */.parseStructureWithMaybePermits/* : (arg0 : string, arg1 : CompileState, arg2 : string, arg3 : string, arg4 : string, arg5 : List<string>) => Option<[CompileState, IncompleteClassSegment]> */(targetInfix/* : string */, state/* : CompileState */, beforeInfix/* : unknown */, beforeContent/* : unknown */, content1/* : string */, annotations/* : List<string> */)/* : Option<[CompileState, IncompleteClassSegment]> */;
					})/* : Option<T> */.or/* : (arg0 : () => Option<T>) => Option<T> */(() => {
						return this/* : Main */.parseStructureWithMaybePermits/* : (arg0 : string, arg1 : CompileState, arg2 : string, arg3 : string, arg4 : string, arg5 : List<string>) => Option<[CompileState, IncompleteClassSegment]> */(targetInfix/* : string */, state/* : CompileState */, beforeInfix/* : unknown */, beforeContent/* : unknown */, content1/* : string */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */)/* : Option<[CompileState, IncompleteClassSegment]> */;
					})/* : Option<T> */;
				})/* : Option<T> */;
			})/* : Option<T> */;
		})/* : Option<T> */;
	}
	parseAnnotations(annotationsString : string) : List<string> {
		return this/* : Main */.divideAll/* : (arg0 : string, arg1 : (arg0 : DivideState, arg1 : string) => DivideState) => List<string> */(annotationsString/* : string */.strip/* : unknown */()/* : unknown */, (state1, c) => this/* : Main */.foldByDelimiter/* : (arg0 : DivideState, arg1 : string, arg2 : string) => DivideState */(state1/* : unknown */, c/* : unknown */, "\n")/* : DivideState */)/* : List<string> */.query/* : () => Query<string> */()/* : Query<string> */.map/* : (arg0 : (arg0 : string) => R) => Query<R> */(/* String */.strip/* : unknown */)/* : Query<R> */.filter/* : (arg0 : (arg0 : T) => boolean) => Option<T> */((value : T) => !value/* : T */.isEmpty/* : unknown */()/* : unknown */)/* : Option<T> */.map/* : (arg0 : (arg0 : T) => R) => Option<R> */((value : T) => value/* : T */.substring/* : unknown */(1/* : number */)/* : unknown */)/* : Option<R> */.map/* : unknown */(/* String */.strip/* : unknown */)/* : unknown */.filter/* : unknown */((value) => !value/* : T */.isEmpty/* : unknown */()/* : unknown */)/* : unknown */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */;
	}
	foldByDelimiter(state1 : DivideState, c : string, delimiter : string) : DivideState {
		if (c/* : string */ === delimiter/* : string */){
			return state1/* : DivideState */.advance/* : () => DivideState */()/* : DivideState */;
		}
		return state1/* : DivideState */.append/* : (arg0 : string) => DivideState */(c/* : string */)/* : DivideState */;
	}
	parseStructureWithMaybePermits(targetInfix : string, state : CompileState, beforeInfix : string, beforeContent : string, content1 : string, annotations : List<string>) : Option<[CompileState, IncompleteClassSegment]> {
		return this/* : Main */.last/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(beforeContent/* : string */, " permits ", (s, s2) => {
			let variants = this/* : Main */.divideAll/* : (arg0 : string, arg1 : (arg0 : DivideState, arg1 : string) => DivideState) => List<string> */(s2/* : unknown */, this/* : Main */.foldValueChar/* : unknown */)/* : List<string> */.query/* : () => Query<string> */()/* : Query<string> */.map/* : (arg0 : (arg0 : string) => R) => Query<R> */(/* String */.strip/* : unknown */)/* : Query<R> */.filter/* : (arg0 : (arg0 : T) => boolean) => Option<T> */((value : T) => !value/* : T */.isEmpty/* : unknown */()/* : unknown */)/* : Option<T> */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */;
			return this/* : Main */.parseStructureWithMaybeImplements/* : (arg0 : string, arg1 : CompileState, arg2 : string, arg3 : string, arg4 : string, arg5 : List<string>, arg6 : List<string>) => Option<[CompileState, IncompleteClassSegment]> */(targetInfix/* : string */, state/* : CompileState */, beforeInfix/* : string */, s/* : unknown */, content1/* : string */, variants/* : unknown */, annotations/* : List<string> */)/* : Option<[CompileState, IncompleteClassSegment]> */;
		})/* : Option<T> */.or/* : (arg0 : () => Option<T>) => Option<T> */(() => {
			return this/* : Main */.parseStructureWithMaybeImplements/* : (arg0 : string, arg1 : CompileState, arg2 : string, arg3 : string, arg4 : string, arg5 : List<string>, arg6 : List<string>) => Option<[CompileState, IncompleteClassSegment]> */(targetInfix/* : string */, state/* : CompileState */, beforeInfix/* : string */, beforeContent/* : string */, content1/* : string */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, annotations/* : List<string> */)/* : Option<[CompileState, IncompleteClassSegment]> */;
		})/* : Option<T> */;
	}
	parseStructureWithMaybeImplements(targetInfix : string, state : CompileState, beforeInfix : string, beforeContent : string, content1 : string, variants : List<string>, annotations : List<string>) : Option<[CompileState, IncompleteClassSegment]> {
		return this/* : Main */.first/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(beforeContent/* : string */, " implements ", (s, s2) => {
			return this/* : Main */.parseValues/* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) => Option<[CompileState, List<T>]> */(state/* : CompileState */, s2/* : unknown */, this/* : Main */.parseType/* : unknown */)/* : Option<[CompileState, List<T>]> */.flatMap/* : (arg0 : (arg0 : [CompileState, List<T>]) => Option<R>) => Option<R> */((interfaces : [CompileState, List<T>]) => {
				return this/* : Main */.parseStructureWithMaybeExtends/* : (arg0 : string, arg1 : CompileState, arg2 : string, arg3 : string, arg4 : string, arg5 : List<string>, arg6 : List<string>, arg7 : List<Type>) => Option<[CompileState, IncompleteClassSegment]> */(targetInfix/* : string */, state/* : CompileState */, beforeInfix/* : string */, s/* : unknown */, content1/* : string */, variants/* : List<string> */, annotations/* : List<string> */, interfaces/* : [CompileState, List<T>] */[1/* : number */])/* : Option<[CompileState, IncompleteClassSegment]> */;
			})/* : Option<R> */;
		})/* : Option<T> */.or/* : (arg0 : () => Option<T>) => Option<T> */(() => {
			return this/* : Main */.parseStructureWithMaybeExtends/* : (arg0 : string, arg1 : CompileState, arg2 : string, arg3 : string, arg4 : string, arg5 : List<string>, arg6 : List<string>, arg7 : List<Type>) => Option<[CompileState, IncompleteClassSegment]> */(targetInfix/* : string */, state/* : CompileState */, beforeInfix/* : string */, beforeContent/* : string */, content1/* : string */, variants/* : List<string> */, annotations/* : List<string> */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */)/* : Option<[CompileState, IncompleteClassSegment]> */;
		})/* : Option<T> */;
	}
	parseStructureWithMaybeExtends(targetInfix : string, state : CompileState, beforeInfix : string, beforeContent : string, content1 : string, variants : List<string>, annotations : List<string>, interfaces : List<Type>) : Option<[CompileState, IncompleteClassSegment]> {
		return this/* : Main */.first/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(beforeContent/* : string */, " extends ", (s, s2) => {
			return this/* : Main */.parseValues/* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) => Option<[CompileState, List<T>]> */(state/* : CompileState */, s2/* : unknown */, this/* : Main */.parseType/* : unknown */)/* : Option<[CompileState, List<T>]> */.flatMap/* : (arg0 : (arg0 : [CompileState, List<T>]) => Option<R>) => Option<R> */((inner : [CompileState, List<T>]) => {
				return this/* : Main */.parseStructureWithMaybeParams/* : (arg0 : string, arg1 : CompileState, arg2 : string, arg3 : string, arg4 : string, arg5 : List<string>, arg6 : List<string>, arg7 : List<Type>, arg8 : List<Type>) => Option<[CompileState, IncompleteClassSegment]> */(targetInfix/* : string */, inner/* : [CompileState, List<T>] */[0/* : number */], beforeInfix/* : string */, s/* : unknown */, content1/* : string */, variants/* : List<string> */, annotations/* : List<string> */, inner/* : [CompileState, List<T>] */[1/* : number */], interfaces/* : List<Type> */)/* : Option<[CompileState, IncompleteClassSegment]> */;
			})/* : Option<R> */;
		})/* : Option<T> */.or/* : (arg0 : () => Option<T>) => Option<T> */(() => {
			return this/* : Main */.parseStructureWithMaybeParams/* : (arg0 : string, arg1 : CompileState, arg2 : string, arg3 : string, arg4 : string, arg5 : List<string>, arg6 : List<string>, arg7 : List<Type>, arg8 : List<Type>) => Option<[CompileState, IncompleteClassSegment]> */(targetInfix/* : string */, state/* : CompileState */, beforeInfix/* : string */, beforeContent/* : string */, content1/* : string */, variants/* : List<string> */, annotations/* : List<string> */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, interfaces/* : List<Type> */)/* : Option<[CompileState, IncompleteClassSegment]> */;
		})/* : Option<T> */;
	}
	parseStructureWithMaybeParams(targetInfix : string, state : CompileState, beforeInfix : string, beforeContent : string, content1 : string, variants : List<string>, annotations : List<string>, superTypes : List<Type>, interfaces : List<Type>) : Option<[CompileState, IncompleteClassSegment]> {
		return this/* : Main */.suffix/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(beforeContent/* : string */.strip/* : unknown */()/* : unknown */, ")", (s : string) => {
			return this/* : Main */.first/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(s/* : string */, "(", (s1, s2) => {
				let parsed : [CompileState, List<Parameter>] = this/* : Main */.parseParameters/* : (arg0 : CompileState, arg1 : string) => [CompileState, List<Parameter>] */(state/* : CompileState */, s2/* : unknown */)/* : [CompileState, List<Parameter>] */;
				return this/* : Main */.parseStructureWithMaybeTypeParams/* : (arg0 : string, arg1 : CompileState, arg2 : string, arg3 : string, arg4 : string, arg5 : List<Parameter>, arg6 : List<string>, arg7 : List<string>, arg8 : List<Type>, arg9 : List<Type>) => Option<[CompileState, IncompleteClassSegment]> */(targetInfix/* : string */, parsed/* : [CompileState, List<Parameter>] */[0/* : number */], beforeInfix/* : string */, s1/* : unknown */, content1/* : string */, parsed/* : [CompileState, List<Parameter>] */[1/* : number */], variants/* : List<string> */, annotations/* : List<string> */, interfaces/* : List<Type> */, superTypes/* : List<Type> */)/* : Option<[CompileState, IncompleteClassSegment]> */;
			})/* : Option<T> */;
		})/* : Option<T> */.or/* : (arg0 : () => Option<T>) => Option<T> */(() => {
			return this/* : Main */.parseStructureWithMaybeTypeParams/* : (arg0 : string, arg1 : CompileState, arg2 : string, arg3 : string, arg4 : string, arg5 : List<Parameter>, arg6 : List<string>, arg7 : List<string>, arg8 : List<Type>, arg9 : List<Type>) => Option<[CompileState, IncompleteClassSegment]> */(targetInfix/* : string */, state/* : CompileState */, beforeInfix/* : string */, beforeContent/* : string */, content1/* : string */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, variants/* : List<string> */, annotations/* : List<string> */, interfaces/* : List<Type> */, superTypes/* : List<Type> */)/* : Option<[CompileState, IncompleteClassSegment]> */;
		})/* : Option<T> */;
	}
	parseStructureWithMaybeTypeParams(targetInfix : string, state : CompileState, beforeInfix : string, beforeContent : string, content1 : string, params : List<Parameter>, variants : List<string>, annotations : List<string>, interfaces : List<Type>, maybeSuperType : List<Type>) : Option<[CompileState, IncompleteClassSegment]> {
		return this/* : Main */.first/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(beforeContent/* : string */, "<", (name, withTypeParams) => {
			return this/* : Main */.first/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(withTypeParams/* : unknown */, ">", (typeParamsString, afterTypeParams) => {
				let readonly mapper : (arg0 : CompileState, arg1 : string) => [CompileState, string] = (state1, s) => [state1/* : unknown */, s/* : unknown */.strip/* : unknown */()/* : unknown */];
				let typeParams : [CompileState, List<T>] = this/* : Main */.parseValuesOrEmpty/* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) => [CompileState, List<T>] */(state/* : CompileState */, typeParamsString/* : unknown */, (state1, s) => new Some(mapper/* : (arg0 : CompileState, arg1 : string) => [CompileState, string] */(state1/* : unknown */, s/* : unknown */)/* : [CompileState, string] */)/* : Some */)/* : [CompileState, List<T>] */;
				return this/* : Main */.assembleStructure/* : (arg0 : CompileState, arg1 : string, arg2 : List<string>, arg3 : string, arg4 : string, arg5 : string, arg6 : List<string>, arg7 : string, arg8 : List<Parameter>, arg9 : List<string>, arg10 : List<Type>, arg11 : List<Type>) => Option<[CompileState, IncompleteClassSegment]> */(typeParams/* : [CompileState, List<T>] */[0/* : number */], targetInfix/* : string */, annotations/* : List<string> */, beforeInfix/* : string */, name/* : unknown */, content1/* : string */, typeParams/* : [CompileState, List<T>] */[1/* : number */], afterTypeParams/* : unknown */, params/* : List<Parameter> */, variants/* : List<string> */, interfaces/* : List<Type> */, maybeSuperType/* : List<Type> */)/* : Option<[CompileState, IncompleteClassSegment]> */;
			})/* : Option<T> */;
		})/* : Option<T> */.or/* : (arg0 : () => Option<T>) => Option<T> */(() => {
			return this/* : Main */.assembleStructure/* : (arg0 : CompileState, arg1 : string, arg2 : List<string>, arg3 : string, arg4 : string, arg5 : string, arg6 : List<string>, arg7 : string, arg8 : List<Parameter>, arg9 : List<string>, arg10 : List<Type>, arg11 : List<Type>) => Option<[CompileState, IncompleteClassSegment]> */(state/* : CompileState */, targetInfix/* : string */, annotations/* : List<string> */, beforeInfix/* : string */, beforeContent/* : string */, content1/* : string */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, "", params/* : List<Parameter> */, variants/* : List<string> */, interfaces/* : List<Type> */, maybeSuperType/* : List<Type> */)/* : Option<[CompileState, IncompleteClassSegment]> */;
		})/* : Option<T> */;
	}
	assembleStructure(state : CompileState, targetInfix : string, annotations : List<string>, beforeInfix : string, rawName : string, content : string, typeParams : List<string>, after : string, rawParameters : List<Parameter>, variants : List<string>, interfaces : List<Type>, maybeSuperType : List<Type>) : Option<[CompileState, IncompleteClassSegment]> {
		let name = rawName/* : string */.strip/* : unknown */()/* : unknown */;
		if (!isSymbol/* : (arg0 : string) => boolean */(name/* : unknown */)/* : unknown */){
			return new None()/* : None */;
		}
		if (annotations/* : List<string> */.contains/* : (arg0 : string) => boolean */("Actual")/* : boolean */){
			return new Some([state/* : CompileState */, new Whitespace()/* : Whitespace */])/* : Some */;
		}
		let segmentsTuple : [CompileState, List<T>] = this/* : Main */.parseStatements/* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => [CompileState, T]) => [CompileState, List<T>] */(state/* : CompileState */.pushStructName/* : (arg0 : Tuple2Impl<string, List<string>>) => CompileState */([name/* : unknown */, variants/* : List<string> */])/* : CompileState */.withTypeParams/* : unknown */(typeParams/* : List<string> */)/* : unknown */, content/* : string */, (state0, input) => this/* : Main */.parseClassSegment/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, IncompleteClassSegment] */(state0/* : unknown */, input/* : unknown */, 1/* : number */)/* : [CompileState, IncompleteClassSegment] */)/* : [CompileState, List<T>] */;
		let segmentsState = segmentsTuple/* : [CompileState, List<T>] */[0/* : number */];
		let segments = segmentsTuple/* : [CompileState, List<T>] */[1/* : number */];
		let parameters : List<Definition> = this/* : Main */.retainDefinitions/* : (arg0 : List<Parameter>) => List<Definition> */(rawParameters/* : List<Parameter> */)/* : List<Definition> */;
		let prototype : StructurePrototype = new StructurePrototype(targetInfix/* : string */, beforeInfix/* : string */, name/* : unknown */, typeParams/* : List<string> */, parameters/* : List<Definition> */, after/* : string */, segments/* : unknown */, variants/* : List<string> */, interfaces/* : List<Type> */, maybeSuperType/* : List<Type> */)/* : StructurePrototype */;
		return new Some([segmentsState/* : unknown */.addType/* : unknown */(prototype/* : StructurePrototype */.createObjectType/* : () => ObjectType */()/* : ObjectType */)/* : unknown */, prototype/* : StructurePrototype */])/* : Some */;
	}
	completeStructure(state : CompileState, prototype : StructurePrototype) : Option<[CompileState, ClassSegment]> {
		let thisType : ObjectType = prototype/* : StructurePrototype */.createObjectType/* : () => ObjectType */()/* : ObjectType */;
		let state2 : CompileState = state/* : CompileState */.enterDefinitions/* : () => CompileState */()/* : CompileState */.define/* : (arg0 : Definition) => CompileState */(ImmutableDefinition/* : ImmutableDefinition */.createSimpleDefinition/* : (arg0 : string, arg1 : Type) => Definition */("this", thisType/* : ObjectType */)/* : Definition */)/* : CompileState */;
		let bases = prototype/* : StructurePrototype */.interfaces/* : List<Type> */.query/* : () => Query<Type> */()/* : Query<Type> */.map/* : (arg0 : (arg0 : Type) => R) => Query<R> */(Main/* : Main */.retainFindableType/* : unknown */)/* : Query<R> */.flatMap/* : (arg0 : (arg0 : T) => Option<R>) => Option<R> */(Queries/* : Queries */.fromOption/* : unknown */)/* : Option<R> */.map/* : (arg0 : (arg0 : T) => R) => Option<R> */(FindableType/* : FindableType */.findBase/* : unknown */)/* : Option<R> */.flatMap/* : unknown */(Queries/* : Queries */.fromOption/* : unknown */)/* : unknown */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */;
		let variantsSuper = bases/* : unknown */.query/* : unknown */()/* : unknown */.filter/* : unknown */((type) => type/* : unknown */.hasVariant/* : unknown */(prototype/* : StructurePrototype */.name/* : string */)/* : unknown */)/* : unknown */.map/* : unknown */(BaseType/* : BaseType */.findName/* : unknown */)/* : unknown */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */;
		return this/* : Main */.mapUsingState/* : (arg0 : CompileState, arg1 : List<T>, arg2 : (arg0 : CompileState, arg1 : [number, T]) => Option<[CompileState, R]>) => Option<[CompileState, List<R>]> */(state2/* : CompileState */, prototype/* : StructurePrototype */.segments/* : List<IncompleteClassSegment> */()/* : unknown */, (state1, entry) => this/* : Main */.completeClassSegment/* : (arg0 : CompileState, arg1 : IncompleteClassSegment) => Option<[CompileState, ClassSegment]> */(state1/* : unknown */, entry/* : unknown */.right/* : unknown */()/* : unknown */)/* : Option<[CompileState, ClassSegment]> */)/* : Option<[CompileState, List<R>]> */.map/* : (arg0 : (arg0 : [CompileState, List<R>]) => R) => Option<R> */((oldStatementsTuple : [CompileState, List<R>]) => {
			let oldStatementsState = oldStatementsTuple/* : [CompileState, List<R>] */[0/* : number */];
			let oldStatements = oldStatementsTuple/* : [CompileState, List<R>] */[1/* : number */];
			let exited = oldStatementsState/* : unknown */.exitDefinitions/* : unknown */()/* : unknown */;
			let fold = variantsSuper/* : unknown */.query/* : unknown */()/* : unknown */.fold/* : unknown */(oldStatements/* : unknown */, (classSegmentList, superType) => {
				let name = superType/* : unknown */ + "Variant";
				let type : ObjectType = new ObjectType(name/* : unknown */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */)/* : ObjectType */;
				let definition : Definition = this/* : Main */.createVariantDefinition/* : (arg0 : ObjectType) => Definition */(type/* : unknown */)/* : Definition */;
				return classSegmentList/* : unknown */.addFirst/* : unknown */(new Statement(1/* : number */, new FieldInitialization(definition/* : Definition */, new SymbolValue(name/* : unknown */ + "." + prototype/* : StructurePrototype */.name/* : unknown */, type/* : unknown */)/* : SymbolValue */)/* : FieldInitialization */)/* : Statement */)/* : unknown */;
			})/* : unknown */;
			/* CompileState withEnum */;
			/* List<ClassSegment> newSegments */;
			if (prototype/* : StructurePrototype */.variants/* : List<string> */.isEmpty/* : () => boolean */()/* : boolean */){
				/* withEnum */ = exited/* : unknown */;
				/* newSegments */ = fold/* : unknown */;
			}
			else {
				let joined = prototype/* : StructurePrototype */.variants/* : List<string> */.query/* : () => Query<string> */()/* : Query<string> */.map/* : (arg0 : (arg0 : string) => R) => Query<R> */((inner : string) => "\n\t" + inner/* : string */)/* : Query<R> */.collect/* : unknown */(new Joiner(",")/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
				/* withEnum */ = exited/* : unknown */.addStructure/* : unknown */("enum " + prototype.name + "Variant" + " {" +
                        joined +
                        "\n}\n")/* : unknown */;
				let definition : Definition = this/* : Main */.createVariantDefinition/* : (arg0 : ObjectType) => Definition */(new ObjectType(prototype/* : StructurePrototype */.name/* : string */ + "Variant", Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, prototype/* : StructurePrototype */.variants/* : List<string> */)/* : ObjectType */)/* : Definition */;
				/* newSegments */ = fold/* : unknown */.addFirst/* : unknown */(new Statement(1/* : number */, definition/* : Definition */)/* : Statement */)/* : unknown */;
			}
			let segmentsWithMaybeConstructor : R = this/* : Main */.attachConstructor/* : (arg0 : StructurePrototype, arg1 : List<ClassSegment>) => List<ClassSegment> */(prototype/* : StructurePrototype */, /* newSegments */)/* : List<ClassSegment> */.query/* : () => Query<ClassSegment> */()/* : Query<ClassSegment> */.flatMap/* : (arg0 : (arg0 : ClassSegment) => Query<R>) => Query<R> */((segment : ClassSegment) => this/* : Main */.flattenEnumValues/* : (arg0 : ClassSegment, arg1 : ObjectType) => Query<ClassSegment> */(segment/* : ClassSegment */, thisType/* : ObjectType */)/* : Query<ClassSegment> */)/* : Query<R> */.collect/* : (arg0 : Collector<T, R>) => R */(new ListCollector()/* : ListCollector */)/* : R */;
			let generatedSegments = segmentsWithMaybeConstructor/* : R */.query/* : unknown */()/* : unknown */.map/* : unknown */(ClassSegment/* : ClassSegment */.generate/* : unknown */)/* : unknown */.collect/* : unknown */(Joiner/* : Joiner */.empty/* : () => Joiner */()/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
			let joinedTypeParams : string = prototype/* : StructurePrototype */.joinTypeParams/* : () => string */()/* : string */;
			let interfacesJoined : string = prototype/* : StructurePrototype */.joinInterfaces/* : () => string */()/* : string */;
			let generatedSuperType = prototype/* : StructurePrototype */.superTypes/* : List<Type> */.query/* : () => Query<Type> */()/* : Query<Type> */.map/* : (arg0 : (arg0 : Type) => R) => Query<R> */(Type/* : Type */.generate/* : unknown */)/* : Query<R> */.collect/* : unknown */(new Joiner(", ")/* : Joiner */)/* : unknown */.map/* : unknown */((generated) => " extends " + generated/* : unknown */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
			let currentTypesDebugged = /*  isDebugEnabled ? this.formatCurrentTypesDebugged(withEnum) : "" */;
			let generated = currentTypesDebugged/* : unknown */ + generatePlaceholder/* : (arg0 : string) => string */(prototype/* : StructurePrototype */.beforeInfix/* : string */()/* : unknown */.strip/* : unknown */()/* : unknown */)/* : string */ + prototype/* : StructurePrototype */.targetInfix/* : string */()/* : unknown */ + prototype/* : StructurePrototype */.name/* : string */()/* : unknown */ + joinedTypeParams/* : string */ + generatePlaceholder/* : (arg0 : string) => string */(prototype/* : StructurePrototype */.after/* : string */()/* : unknown */)/* : string */ + generatedSuperType/* : unknown */ + interfacesJoined/* : string */ + " {" + generatedSegments + "\n}\n";
			let compileState = /* withEnum */.popStructName/* : unknown */()/* : unknown */;
			let definedState = compileState/* : unknown */.addStructure/* : unknown */(generated/* : unknown */)/* : unknown */;
			return [definedState/* : unknown */, new Whitespace()/* : Whitespace */];
		})/* : Option<R> */;
	}
	formatCurrentTypesDebugged(withEnum : CompileState) : string {
		return withEnum/* : CompileState */.objectTypes/* : List<ObjectType> */.query/* : () => Query<ObjectType> */()/* : Query<ObjectType> */.map/* : (arg0 : (arg0 : ObjectType) => R) => Query<R> */(ObjectType/* : ObjectType */.name/* : unknown */)/* : Query<R> */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */.sort/* : unknown */(/* String */.compareTo/* : unknown */)/* : unknown */.query/* : unknown */()/* : unknown */.collect/* : unknown */(new Joiner(",\n\t")/* : Joiner */)/* : unknown */.map/* : unknown */((inner) => "[" + inner + "\n]")/* : unknown */.map/* : unknown */(Main/* : Main */.generatePlaceholder/* : unknown */)/* : unknown */.map/* : unknown */((inner) => inner/* : unknown */ + "\n")/* : unknown */.orElse/* : unknown */("")/* : unknown */;
	}
	flattenEnumValues(segment : ClassSegment, thisType : ObjectType) : Query<ClassSegment> {
		if (segment/* : ClassSegment */._ClassSegmentVariant/* : unknown */ === ClassSegmentVariant.EnumValues/* : unknown */){
			let enumValues : EnumValues = segment/* : ClassSegment */ as EnumValues;
			return enumValues/* : EnumValues */.values/* : List<EnumValue> */.query/* : () => Query<EnumValue> */()/* : Query<EnumValue> */.map/* : (arg0 : (arg0 : EnumValue) => R) => Query<R> */((enumValue : EnumValue) => {
				let definition : ImmutableDefinition = new ImmutableDefinition(Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, Lists/* : Lists */.of/* : (arg0 : T[]) => List<T> */("static")/* : List<T> */, enumValue/* : EnumValue */.value/* : unknown */, thisType/* : ObjectType */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */)/* : ImmutableDefinition */;
				return new Statement(1/* : number */, new FieldInitialization(definition/* : ImmutableDefinition */, new Invokable(new ConstructionCaller(thisType/* : ObjectType */)/* : ConstructionCaller */, enumValue/* : EnumValue */.values/* : unknown */, thisType/* : ObjectType */)/* : Invokable */)/* : FieldInitialization */)/* : Statement */;
			})/* : Query<R> */;
		}
		return Queries/* : Queries */.from/* : (arg0 : T[]) => Query<T> */(segment/* : ClassSegment */)/* : Query<T> */;
	}
	createVariantDefinition(type : ObjectType) : Definition {
		return ImmutableDefinition/* : ImmutableDefinition */.createSimpleDefinition/* : (arg0 : string, arg1 : Type) => Definition */("_" + type/* : ObjectType */.name/* : unknown */, type/* : ObjectType */)/* : Definition */;
	}
	attachConstructor(prototype : StructurePrototype, segments : List<ClassSegment>) : List<ClassSegment> {
		let parameters = prototype/* : StructurePrototype */.parameters/* : List<Definition> */()/* : unknown */;
		if (parameters/* : unknown */.isEmpty/* : unknown */()/* : unknown */){
			return segments/* : List<ClassSegment> */;
		}
		let definitions : List<ClassSegment> = parameters/* : unknown */.query/* : unknown */()/* : unknown */./* : unknown */ < /* ClassSegment>map */((definition) => new Statement(1/* : number */, definition/* : unknown */)/* : Statement */)/* : unknown */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */;
		let collect = /* parameters.query()
                .map(definition  */ - /* > {
                    var destination = new DataAccess(new SymbolValue("this", Primitive.Unknown), definition.findName(), Primitive.Unknown);
                    return new Assignment */(/* destination */, /*  new SymbolValue(definition.findName(), Primitive.Unknown));
                } */)/* : unknown */./* : unknown */ < /* FunctionSegment>map */((assignment) => new Statement(2/* : number */, assignment/* : unknown */)/* : Statement */)/* : unknown */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */;
		let func : FunctionNode = new FunctionNode(1/* : number */, new ConstructorHeader()/* : ConstructorHeader */, parameters/* : unknown */, new Some(collect/* : unknown */)/* : Some */)/* : FunctionNode */;
		return segments/* : List<ClassSegment> */.addFirst/* : (arg0 : ClassSegment) => List<ClassSegment> */(func/* : FunctionNode */)/* : List<ClassSegment> */.addAllFirst/* : unknown */(definitions/* : List<ClassSegment> */)/* : unknown */;
	}
	completeClassSegment(state1 : CompileState, segment : IncompleteClassSegment) : Option<[CompileState, ClassSegment]> {
		/* return switch (segment) */{
			/* case IncompleteClassSegmentWrapper wrapper -> new Some<>(new Tuple2Impl<>(state1, wrapper.segment)) */;
			/* case MethodPrototype methodPrototype -> this.completeMethod(state1, methodPrototype) */;
			/* case Whitespace whitespace -> new Some<>(new Tuple2Impl<>(state1, whitespace)) */;
			/* case Placeholder placeholder -> new Some<>(new Tuple2Impl<>(state1, placeholder)) */;
			/* case ClassDefinition classDefinition -> this.completeDefinition(state1, classDefinition) */;
			/* case ClassInitialization classInitialization -> this.completeInitialization(state1, classInitialization) */;
			/* case StructurePrototype structurePrototype -> this.completeStructure(state1, structurePrototype) */;
			/* case EnumValues enumValues -> new Some<>(new Tuple2Impl<>(state1, enumValues)) */;
		}
		/*  */;
	}
	completeInitialization(state1 : CompileState, classInitialization : ClassInitialization) : Option<[CompileState, ClassSegment]> {
		let definition : Definition = classInitialization/* : ClassInitialization */.definition/* : Definition */;
		let statement : Statement = new Statement(classInitialization/* : ClassInitialization */.depth/* : number */, new FieldInitialization(definition/* : Definition */, classInitialization/* : ClassInitialization */.value/* : Value */)/* : FieldInitialization */)/* : Statement */;
		return new Some([state1/* : CompileState */, statement/* : Statement */])/* : Some */;
	}
	completeDefinition(state1 : CompileState, classDefinition : ClassDefinition) : Option<[CompileState, ClassSegment]> {
		let definition : Definition = classDefinition/* : ClassDefinition */.definition/* : Definition */;
		let statement : Statement = new Statement(classDefinition/* : ClassDefinition */.depth/* : number */, definition/* : Definition */)/* : Statement */;
		return new Some([state1/* : CompileState */, statement/* : Statement */])/* : Some */;
	}
	retainDefinition(parameter : Parameter) : Option<Definition> {
		if (parameter/* : Parameter */._ParameterVariant/* : unknown */ === ParameterVariant.Definition/* : unknown */){
			let definition : Definition = parameter/* : Parameter */ as Definition;
			return new Some(definition/* : Definition */)/* : Some */;
		}
		return new None()/* : None */;
	}
	prefix<T>(input : string, prefix : string, mapper : (arg0 : string) => Option<T>) : Option<T> {
		if (!input/* : string */.startsWith/* : unknown */(prefix/* : string */)/* : unknown */){
			return new None()/* : None */;
		}
		let slice = input/* : string */.substring/* : unknown */(Strings/* : Strings */.length/* : (arg0 : string) => number */(prefix/* : string */)/* : number */)/* : unknown */;
		return mapper/* : (arg0 : string) => Option<T> */(slice/* : unknown */)/* : Option<T> */;
	}
	suffix<T>(input : string, suffix : string, mapper : (arg0 : string) => Option<T>) : Option<T> {
		if (!input/* : string */.endsWith/* : unknown */(suffix/* : string */)/* : unknown */){
			return new None()/* : None */;
		}
		let slice = input/* : string */.substring/* : unknown */(0/* : number */, Strings/* : Strings */.length/* : (arg0 : string) => number */(input/* : string */)/* : number */ - Strings/* : Strings */.length/* : unknown */(suffix/* : string */)/* : unknown */)/* : unknown */;
		return mapper/* : (arg0 : string) => Option<T> */(slice/* : unknown */)/* : Option<T> */;
	}
	parseClassSegment(state : CompileState, input : string, depth : number) : [CompileState, IncompleteClassSegment] {
		return this/* : Main */./* : unknown */ < /* Whitespace, IncompleteClassSegment>typed */(() => parseWhitespace/* : (arg0 : string, arg1 : CompileState) => Option<[CompileState, Whitespace]> */(input/* : string */, state/* : CompileState */)/* : Option<[CompileState, Whitespace]> */)/* : unknown */.or/* : unknown */(() => this/* : Main */.typed/* : (arg0 : () => Option<[CompileState, T]>) => Option<[CompileState, S]> */(() => this/* : Main */.parseClass/* : (arg0 : string, arg1 : CompileState) => Option<[CompileState, IncompleteClassSegment]> */(input/* : string */, state/* : CompileState */)/* : Option<[CompileState, IncompleteClassSegment]> */)/* : Option<[CompileState, S]> */)/* : unknown */.or/* : unknown */(() => this/* : Main */.typed/* : (arg0 : () => Option<[CompileState, T]>) => Option<[CompileState, S]> */(() => this/* : Main */.parseStructure/* : (arg0 : string, arg1 : string, arg2 : string, arg3 : CompileState) => Option<[CompileState, IncompleteClassSegment]> */(input/* : string */, "interface ", "interface ", state/* : CompileState */)/* : Option<[CompileState, IncompleteClassSegment]> */)/* : Option<[CompileState, S]> */)/* : unknown */.or/* : unknown */(() => this/* : Main */.typed/* : (arg0 : () => Option<[CompileState, T]>) => Option<[CompileState, S]> */(() => this/* : Main */.parseStructure/* : (arg0 : string, arg1 : string, arg2 : string, arg3 : CompileState) => Option<[CompileState, IncompleteClassSegment]> */(input/* : string */, "record ", "class ", state/* : CompileState */)/* : Option<[CompileState, IncompleteClassSegment]> */)/* : Option<[CompileState, S]> */)/* : unknown */.or/* : unknown */(() => this/* : Main */.typed/* : (arg0 : () => Option<[CompileState, T]>) => Option<[CompileState, S]> */(() => this/* : Main */.parseStructure/* : (arg0 : string, arg1 : string, arg2 : string, arg3 : CompileState) => Option<[CompileState, IncompleteClassSegment]> */(input/* : string */, "enum ", "class ", state/* : CompileState */)/* : Option<[CompileState, IncompleteClassSegment]> */)/* : Option<[CompileState, S]> */)/* : unknown */.or/* : unknown */(() => this/* : Main */.typed/* : (arg0 : () => Option<[CompileState, T]>) => Option<[CompileState, S]> */(() => this/* : Main */.parseField/* : (arg0 : string, arg1 : number, arg2 : CompileState) => Option<[CompileState, IncompleteClassSegment]> */(input/* : string */, depth/* : number */, state/* : CompileState */)/* : Option<[CompileState, IncompleteClassSegment]> */)/* : Option<[CompileState, S]> */)/* : unknown */.or/* : unknown */(() => this/* : Main */.parseMethod/* : (arg0 : CompileState, arg1 : string, arg2 : number) => Option<[CompileState, IncompleteClassSegment]> */(state/* : CompileState */, input/* : string */, depth/* : number */)/* : Option<[CompileState, IncompleteClassSegment]> */)/* : unknown */.or/* : unknown */(() => this/* : Main */.parseEnumValues/* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, IncompleteClassSegment]> */(state/* : CompileState */, input/* : string */)/* : Option<[CompileState, IncompleteClassSegment]> */)/* : unknown */.orElseGet/* : unknown */(() => [state/* : CompileState */, new Placeholder(input/* : string */)/* : Placeholder */])/* : unknown */;
	}
	parseEnumValues(state : CompileState, input : string) : Option<[CompileState, IncompleteClassSegment]> {
		return this/* : Main */.suffix/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(input/* : string */.strip/* : unknown */()/* : unknown */, ";", (withoutEnd : string) => {
			return this/* : Main */.parseValues/* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) => Option<[CompileState, List<T>]> */(state/* : CompileState */, withoutEnd/* : string */, (state2, enumValue) => {
				return this/* : Main */.suffix/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(enumValue/* : unknown */.strip/* : unknown */()/* : unknown */, ")", (withoutValueEnd : string) => {
					return this/* : Main */.first/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(withoutValueEnd/* : string */, "(", (s4, s2) => {
						return this/* : Main */.parseValues/* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) => Option<[CompileState, List<T>]> */(state2/* : unknown */, s2/* : unknown */, (state1, s1) => new Some(Main/* : Main */.this/* : unknown */.parseArgument/* : unknown */(state1/* : unknown */, s1/* : unknown */, 1/* : number */)/* : unknown */)/* : Some */)/* : Option<[CompileState, List<T>]> */.map/* : (arg0 : (arg0 : [CompileState, List<T>]) => R) => Option<R> */((arguments : [CompileState, List<T>]) => {
							return [arguments/* : [CompileState, List<T>] */[0/* : number */], new EnumValue(s4/* : unknown */, Main/* : Main */.this/* : unknown */.retainValues/* : unknown */(arguments/* : [CompileState, List<T>] */[1/* : number */])/* : unknown */)/* : EnumValue */];
						})/* : Option<R> */;
					})/* : Option<T> */;
				})/* : Option<T> */;
			})/* : Option<[CompileState, List<T>]> */.map/* : (arg0 : (arg0 : [CompileState, List<T>]) => R) => Option<R> */((tuple : [CompileState, List<T>]) => {
				return [tuple/* : [CompileState, List<T>] */[0/* : number */], new EnumValues(tuple/* : [CompileState, List<T>] */[1/* : number */])/* : EnumValues */];
			})/* : Option<R> */;
		})/* : Option<T> */;
	}
	typed<T extends S, S>(action : () => Option<[CompileState, T]>) : Option<[CompileState, S]> {
		return action/* : () => Option<[CompileState, T]> */()/* : Option<[CompileState, T]> */.map/* : (arg0 : (arg0 : [CompileState, T]) => R) => Option<R> */((tuple : [CompileState, T]) => [tuple/* : [CompileState, T] */[0/* : number */], tuple/* : [CompileState, T] */[1/* : number */]])/* : Option<R> */;
	}
	parseMethod(state : CompileState, input : string, depth : number) : Option<[CompileState, IncompleteClassSegment]> {
		return this/* : Main */.first/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(input/* : string */, "(", (definitionString, withParams) => {
			return this/* : Main */.first/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(withParams/* : unknown */, ")", (parametersString, rawContent) => {
				return this/* : Main */.parseDefinition/* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Definition]> */(state/* : CompileState */, definitionString/* : unknown */)/* : Option<[CompileState, Definition]> */./* : unknown */ < Tuple2/* : Tuple2 */ < /* CompileState, Header>>map */((tuple) => [tuple/* : unknown */.left/* : unknown */()/* : unknown */, tuple/* : unknown */.right/* : unknown */()/* : unknown */])/* : unknown */.or/* : unknown */(() => this/* : Main */.parseConstructor/* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Header]> */(state/* : CompileState */, definitionString/* : unknown */)/* : Option<[CompileState, Header]> */)/* : unknown */.flatMap/* : unknown */((definitionTuple) => this/* : Main */.assembleMethod/* : (arg0 : number, arg1 : string, arg2 : string, arg3 : [CompileState, Header]) => Option<[CompileState, IncompleteClassSegment]> */(depth/* : number */, parametersString/* : unknown */, rawContent/* : unknown */, definitionTuple/* : unknown */)/* : Option<[CompileState, IncompleteClassSegment]> */)/* : unknown */;
			})/* : Option<T> */;
		})/* : Option<T> */;
	}
	assembleMethod(depth : number, parametersString : string, rawContent : string, definitionTuple : [CompileState, Header]) : Option<[CompileState, IncompleteClassSegment]> {
		let definitionState = definitionTuple/* : [CompileState, Header] */[0/* : number */];
		let header = definitionTuple/* : [CompileState, Header] */[1/* : number */];
		let parametersTuple : [CompileState, List<Parameter>] = this/* : Main */.parseParameters/* : (arg0 : CompileState, arg1 : string) => [CompileState, List<Parameter>] */(definitionState/* : unknown */, parametersString/* : string */)/* : [CompileState, List<Parameter>] */;
		let rawParameters = parametersTuple/* : [CompileState, List<Parameter>] */[1/* : number */];
		let parameters : List<Definition> = this/* : Main */.retainDefinitions/* : (arg0 : List<Parameter>) => List<Definition> */(rawParameters/* : unknown */)/* : List<Definition> */;
		let prototype : MethodPrototype = new MethodPrototype(depth/* : number */, header/* : unknown */, parameters/* : List<Definition> */, rawContent/* : string */.strip/* : unknown */()/* : unknown */)/* : MethodPrototype */;
		return new Some([parametersTuple/* : [CompileState, List<Parameter>] */[0/* : number */].define/* : unknown */(prototype/* : MethodPrototype */.createDefinition/* : () => Definition */()/* : Definition */)/* : unknown */, prototype/* : MethodPrototype */])/* : Some */;
	}
	completeMethod(state : CompileState, prototype : MethodPrototype) : Option<[CompileState, ClassSegment]> {
		let definition : Definition = prototype/* : MethodPrototype */.createDefinition/* : () => Definition */()/* : Definition */;
		let oldHeader = prototype/* : MethodPrototype */.header/* : Header */()/* : unknown */;
		/* Header newHeader */;
		if (oldHeader/* : unknown */._UnknownVariant/* : unknown */ === UnknownVariant.Definition/* : unknown */){
			let maybeDefinition : Definition = oldHeader/* : unknown */ as Definition;
			/* newHeader */ = maybeDefinition/* : Definition */.removeAnnotations/* : () => Definition */()/* : Definition */;
		}
		else {
			/* newHeader */ = oldHeader/* : unknown */;
		}
		if (prototype/* : MethodPrototype */.content/* : string */()/* : unknown */ === ";" || definition/* : Definition */.containsAnnotation/* : unknown */("Actual")/* : unknown */){
			return new Some([state/* : CompileState */.define/* : (arg0 : Definition) => CompileState */(definition/* : Definition */)/* : CompileState */, new FunctionNode(prototype/* : MethodPrototype */.depth/* : number */()/* : unknown */, /* newHeader */, prototype/* : MethodPrototype */.parameters/* : List<Definition> */()/* : unknown */, new None()/* : None */)/* : FunctionNode */])/* : Some */;
		}
		if (prototype/* : MethodPrototype */.content/* : string */()/* : unknown */.startsWith/* : unknown */("{")/* : unknown */ && prototype/* : MethodPrototype */.content/* : unknown */()/* : unknown */.endsWith/* : unknown */("}")/* : unknown */){
			let substring = prototype/* : MethodPrototype */.content/* : string */()/* : unknown */.substring/* : unknown */(1/* : number */, Strings/* : Strings */.length/* : (arg0 : string) => number */(prototype/* : MethodPrototype */.content/* : string */()/* : unknown */)/* : number */ - 1/* : number */)/* : unknown */;
			let withDefined : CompileState = state/* : CompileState */.enterDefinitions/* : () => CompileState */()/* : CompileState */.defineAll/* : (arg0 : List<Definition>) => CompileState */(prototype/* : MethodPrototype */.parameters/* : List<Definition> */()/* : unknown */)/* : CompileState */;
			let statementsTuple : [CompileState, List<T>] = this/* : Main */.parseStatements/* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => [CompileState, T]) => [CompileState, List<T>] */(withDefined/* : CompileState */, substring/* : unknown */, (state1, input1) => this/* : Main */.parseFunctionSegment/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, FunctionSegment] */(state1/* : unknown */, input1/* : unknown */, prototype/* : MethodPrototype */.depth/* : number */()/* : unknown */ + 1/* : number */)/* : [CompileState, FunctionSegment] */)/* : [CompileState, List<T>] */;
			let statements = statementsTuple/* : [CompileState, List<T>] */[1/* : number */];
			return new Some([statementsTuple/* : [CompileState, List<T>] */[0/* : number */].exitDefinitions/* : unknown */()/* : unknown */.define/* : unknown */(definition/* : Definition */)/* : unknown */, new FunctionNode(prototype/* : MethodPrototype */.depth/* : number */()/* : unknown */, /* newHeader */, prototype/* : MethodPrototype */.parameters/* : List<Definition> */()/* : unknown */, new Some(statements/* : unknown */)/* : Some */)/* : FunctionNode */])/* : Some */;
		}
		return new None()/* : None */;
	}
	parseConstructor(state : CompileState, input : string) : Option<[CompileState, Header]> {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (state/* : CompileState */.isCurrentStructName/* : (arg0 : string) => boolean */(stripped/* : unknown */)/* : boolean */){
			return new Some([state/* : CompileState */, new ConstructorHeader()/* : ConstructorHeader */])/* : Some */;
		}
		return new None()/* : None */;
	}
	retainDefinitions(right : List<Parameter>) : List<Definition> {
		return right/* : List<Parameter> */.query/* : () => Query<Parameter> */()/* : Query<Parameter> */.map/* : (arg0 : (arg0 : Parameter) => R) => Query<R> */(this/* : Main */.retainDefinition/* : unknown */)/* : Query<R> */.flatMap/* : (arg0 : (arg0 : T) => Option<R>) => Option<R> */(Queries/* : Queries */.fromOption/* : unknown */)/* : Option<R> */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */;
	}
	parseParameters(state : CompileState, params : string) : [CompileState, List<Parameter>] {
		return this/* : Main */.parseValuesOrEmpty/* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) => [CompileState, List<T>] */(state/* : CompileState */, params/* : string */, (state1, s) => new Some(this/* : Main */.parseParameter/* : (arg0 : CompileState, arg1 : string) => [CompileState, Parameter] */(state1/* : unknown */, s/* : unknown */)/* : [CompileState, Parameter] */)/* : Some */)/* : [CompileState, List<T>] */;
	}
	parseFunctionSegments(state : CompileState, input : string, depth : number) : [CompileState, List<FunctionSegment>] {
		return this/* : Main */.parseStatements/* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => [CompileState, T]) => [CompileState, List<T>] */(state/* : CompileState */, input/* : string */, (state1, input1) => this/* : Main */.parseFunctionSegment/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, FunctionSegment] */(state1/* : unknown */, input1/* : unknown */, depth/* : number */ + 1/* : number */)/* : [CompileState, FunctionSegment] */)/* : [CompileState, List<T>] */;
	}
	parseFunctionSegment(state : CompileState, input : string, depth : number) : [CompileState, FunctionSegment] {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */.isEmpty/* : unknown */()/* : unknown */){
			return [state/* : CompileState */, new Whitespace()/* : Whitespace */];
		}
		return this/* : Main */.parseFunctionStatement/* : (arg0 : CompileState, arg1 : number, arg2 : string) => Option<[CompileState, FunctionSegment]> */(state/* : CompileState */, depth/* : number */, stripped/* : unknown */)/* : Option<[CompileState, FunctionSegment]> */.or/* : (arg0 : () => Option<[CompileState, FunctionSegment]>) => Option<[CompileState, FunctionSegment]> */(() => this/* : Main */.parseBlock/* : (arg0 : CompileState, arg1 : number, arg2 : string) => Option<[CompileState, FunctionSegment]> */(state/* : CompileState */, depth/* : number */, stripped/* : unknown */)/* : Option<[CompileState, FunctionSegment]> */)/* : Option<[CompileState, FunctionSegment]> */.orElseGet/* : (arg0 : () => T) => T */(() => [state/* : CompileState */, new Placeholder(stripped/* : unknown */)/* : Placeholder */])/* : T */;
	}
	parseFunctionStatement(state : CompileState, depth : number, stripped : string) : Option<[CompileState, FunctionSegment]> {
		return this/* : Main */.suffix/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(stripped/* : string */, ";", (s : string) => {
			let tuple : [CompileState, StatementValue] = this/* : Main */.parseStatementValue/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, StatementValue] */(state/* : CompileState */, s/* : string */, depth/* : number */)/* : [CompileState, StatementValue] */;
			let left = tuple/* : [CompileState, StatementValue] */[0/* : number */];
			let right = tuple/* : [CompileState, StatementValue] */[1/* : number */];
			return new Some([left/* : unknown */, new Statement(depth/* : number */, right/* : unknown */)/* : Statement */])/* : Some */;
		})/* : Option<T> */;
	}
	parseBlock(state : CompileState, depth : number, stripped : string) : Option<[CompileState, FunctionSegment]> {
		return this/* : Main */.suffix/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(stripped/* : string */, "}", (withoutEnd : string) => {
			return this/* : Main */.split/* : (arg0 : () => Option<[string, string]>, arg1 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(() => this/* : Main */.toFirst/* : (arg0 : string) => Option<[string, string]> */(withoutEnd/* : string */)/* : Option<[string, string]> */, (beforeContent, content) => {
				return this/* : Main */.suffix/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(beforeContent/* : unknown */, "{", (headerString : string) => {
					let headerTuple : [CompileState, BlockHeader] = this/* : Main */.parseBlockHeader/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, BlockHeader] */(state/* : CompileState */, headerString/* : string */, depth/* : number */)/* : [CompileState, BlockHeader] */;
					let headerState = headerTuple/* : [CompileState, BlockHeader] */[0/* : number */];
					let header = headerTuple/* : [CompileState, BlockHeader] */[1/* : number */];
					let statementsTuple : [CompileState, List<FunctionSegment>] = this/* : Main */.parseFunctionSegments/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, List<FunctionSegment>] */(headerState/* : unknown */, content/* : unknown */, depth/* : number */)/* : [CompileState, List<FunctionSegment>] */;
					let statementsState = statementsTuple/* : [CompileState, List<FunctionSegment>] */[0/* : number */];
					let statements = statementsTuple/* : [CompileState, List<FunctionSegment>] */[1/* : number */].addAllFirst/* : unknown */(statementsState/* : unknown */.functionSegments/* : unknown */)/* : unknown */;
					return new Some([statementsState/* : unknown */.clearFunctionSegments/* : unknown */()/* : unknown */, new Block(depth/* : number */, header/* : unknown */, statements/* : unknown */)/* : Block */])/* : Some */;
				})/* : Option<T> */;
			})/* : Option<T> */;
		})/* : Option<T> */;
	}
	toFirst(input : string) : Option<[string, string]> {
		let divisions : List<string> = this/* : Main */.divideAll/* : (arg0 : string, arg1 : (arg0 : DivideState, arg1 : string) => DivideState) => List<string> */(input/* : string */, this/* : Main */.foldBlockStart/* : unknown */)/* : List<string> */;
		return divisions/* : List<string> */.removeFirst/* : () => Option<[T, List<T>]> */()/* : Option<[T, List<T>]> */.map/* : (arg0 : (arg0 : [T, List<T>]) => R) => Option<R> */((removed : [T, List<T>]) => {
			let right = removed/* : [T, List<T>] */[0/* : number */];
			let left = removed/* : [T, List<T>] */[1/* : number */].query/* : unknown */()/* : unknown */.collect/* : unknown */(new Joiner("")/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
			return [right/* : unknown */, left/* : unknown */];
		})/* : Option<R> */;
	}
	parseBlockHeader(state : CompileState, input : string, depth : number) : [CompileState, BlockHeader] {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		return this/* : Main */.parseConditional/* : (arg0 : CompileState, arg1 : string, arg2 : string, arg3 : number) => Option<[CompileState, BlockHeader]> */(state/* : CompileState */, stripped/* : unknown */, "if", depth/* : number */)/* : Option<[CompileState, BlockHeader]> */.or/* : (arg0 : () => Option<[CompileState, BlockHeader]>) => Option<[CompileState, BlockHeader]> */(() => this/* : Main */.parseConditional/* : (arg0 : CompileState, arg1 : string, arg2 : string, arg3 : number) => Option<[CompileState, BlockHeader]> */(state/* : CompileState */, stripped/* : unknown */, "while", depth/* : number */)/* : Option<[CompileState, BlockHeader]> */)/* : Option<[CompileState, BlockHeader]> */.or/* : unknown */(() => this/* : Main */.parseElse/* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, BlockHeader]> */(state/* : CompileState */, input/* : string */)/* : Option<[CompileState, BlockHeader]> */)/* : unknown */.orElseGet/* : unknown */(() => [state/* : CompileState */, new Placeholder(stripped/* : unknown */)/* : Placeholder */])/* : unknown */;
	}
	parseElse(state : CompileState, input : string) : Option<[CompileState, BlockHeader]> {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */ === "else"){
			return new Some([state/* : CompileState */, new Else()/* : Else */])/* : Some */;
		}
		return new None()/* : None */;
	}
	parseConditional(state : CompileState, input : string, prefix : string, depth : number) : Option<[CompileState, BlockHeader]> {
		return this/* : Main */.prefix/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(input/* : string */, prefix/* : string */, (withoutPrefix : string) => {
			return this/* : Main */.prefix/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(withoutPrefix/* : string */.strip/* : unknown */()/* : unknown */, "(", (withoutValueStart : string) => {
				return this/* : Main */.suffix/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(withoutValueStart/* : string */, ")", (value : string) => {
					let valueTuple : [CompileState, Value] = this/* : Main */.parseValue/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state/* : CompileState */, value/* : string */, depth/* : number */)/* : [CompileState, Value] */;
					let value1 = valueTuple/* : [CompileState, Value] */[1/* : number */];
					return new Some([valueTuple/* : [CompileState, Value] */[0/* : number */], new Conditional(prefix/* : string */, value1/* : unknown */)/* : Conditional */])/* : Some */;
				})/* : Option<T> */;
			})/* : Option<T> */;
		})/* : Option<T> */;
	}
	foldBlockStart(state : DivideState, c : string) : DivideState {
		let appended : DivideState = state/* : DivideState */.append/* : (arg0 : string) => DivideState */(c/* : string */)/* : DivideState */;
		if (c/* : string */ === "{" && state/* : DivideState */.isLevel/* : unknown */()/* : unknown */){
			return appended/* : DivideState */.advance/* : () => DivideState */()/* : DivideState */;
		}
		if (c/* : string */ === "{"){
			return appended/* : DivideState */.enter/* : unknown */()/* : unknown */;
		}
		if (c/* : string */ === "}"){
			return appended/* : DivideState */.exit/* : unknown */()/* : unknown */;
		}
		return appended/* : DivideState */;
	}
	parseStatementValue(state : CompileState, input : string, depth : number) : [CompileState, StatementValue] {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */.startsWith/* : unknown */("return ")/* : unknown */){
			let value = stripped/* : unknown */.substring/* : unknown */(Strings/* : Strings */.length/* : (arg0 : string) => number */("return ")/* : number */)/* : unknown */;
			let tuple : [CompileState, Value] = this/* : Main */.parseValue/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state/* : CompileState */, value/* : unknown */, depth/* : number */)/* : [CompileState, Value] */;
			let value1 = tuple/* : [CompileState, Value] */[1/* : number */];
			return [tuple/* : [CompileState, Value] */[0/* : number */], new Return(value1/* : unknown */)/* : Return */];
		}
		return this/* : Main */.parseAssignment/* : (arg0 : CompileState, arg1 : number, arg2 : string) => Option<[CompileState, StatementValue]> */(state/* : CompileState */, depth/* : number */, stripped/* : unknown */)/* : Option<[CompileState, StatementValue]> */.orElseGet/* : (arg0 : () => [CompileState, StatementValue]) => [CompileState, StatementValue] */(() => {
			return [state/* : CompileState */, new Placeholder(stripped/* : unknown */)/* : Placeholder */];
		})/* : [CompileState, StatementValue] */;
	}
	parseAssignment(state : CompileState, depth : number, stripped : string) : Option<[CompileState, StatementValue]> {
		return this/* : Main */.first/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(stripped/* : string */, "=", (beforeEquals, valueString) => {
			let sourceTuple : [CompileState, Value] = this/* : Main */.parseValue/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state/* : CompileState */, valueString/* : unknown */, depth/* : number */)/* : [CompileState, Value] */;
			let sourceState = sourceTuple/* : [CompileState, Value] */[0/* : number */];
			let source = sourceTuple/* : [CompileState, Value] */[1/* : number */];
			let destinationTuple : [CompileState, Value] = this/* : Main */.parseValue/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(sourceState/* : unknown */, beforeEquals/* : unknown */, depth/* : number */)/* : [CompileState, Value] */;
			let destinationState = destinationTuple/* : [CompileState, Value] */[0/* : number */];
			let destination = destinationTuple/* : [CompileState, Value] */[1/* : number */];
			return this/* : Main */.parseDefinition/* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Definition]> */(destinationState/* : unknown */, beforeEquals/* : unknown */)/* : Option<[CompileState, Definition]> */.flatMap/* : (arg0 : (arg0 : [CompileState, Definition]) => Option<R>) => Option<R> */((definitionTuple : [CompileState, Definition]) => this/* : Main */.parseInitialization/* : (arg0 : CompileState, arg1 : Definition, arg2 : Value) => Option<[CompileState, StatementValue]> */(definitionTuple/* : [CompileState, Definition] */[0/* : number */], definitionTuple/* : [CompileState, Definition] */[1/* : number */], source/* : unknown */)/* : Option<[CompileState, StatementValue]> */)/* : Option<R> */.or/* : (arg0 : () => Option<T>) => Option<T> */(() => new Some([destinationState/* : unknown */, new Assignment(destination/* : unknown */, source/* : unknown */)/* : Assignment */])/* : Some */)/* : Option<T> */;
		})/* : Option<T> */;
	}
	parseInitialization(state : CompileState, rawDefinition : Definition, source : Value) : Option<[CompileState, StatementValue]> {
		let definition : Definition = rawDefinition/* : Definition */.mapType/* : (arg0 : (arg0 : Type) => Type) => Definition */((type : Type) => {
			if (type/* : Type */ === Primitive/* : Primitive */.Unknown/* : unknown */){
				return source/* : Value */.type/* : () => Type */()/* : Type */;
			}
			else {
				return type/* : Type */;
			}
		})/* : Definition */;
		return new Some([state/* : CompileState */.define/* : (arg0 : Definition) => CompileState */(definition/* : Definition */)/* : CompileState */, new Initialization(definition/* : Definition */, source/* : Value */)/* : Initialization */])/* : Some */;
	}
	parseValue(state : CompileState, input : string, depth : number) : [CompileState, Value] {
		return this/* : Main */.parseBoolean/* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Value]> */(state/* : CompileState */, input/* : string */)/* : Option<[CompileState, Value]> */.or/* : (arg0 : () => Option<[CompileState, Value]>) => Option<[CompileState, Value]> */(() => this/* : Main */.parseLambda/* : (arg0 : CompileState, arg1 : string, arg2 : number) => Option<[CompileState, Value]> */(state/* : CompileState */, input/* : string */, depth/* : number */)/* : Option<[CompileState, Value]> */)/* : Option<[CompileState, Value]> */.or/* : unknown */(() => this/* : Main */.parseString/* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Value]> */(state/* : CompileState */, input/* : string */)/* : Option<[CompileState, Value]> */)/* : unknown */.or/* : unknown */(() => this/* : Main */.parseDataAccess/* : (arg0 : CompileState, arg1 : string, arg2 : number) => Option<[CompileState, Value]> */(state/* : CompileState */, input/* : string */, depth/* : number */)/* : Option<[CompileState, Value]> */)/* : unknown */.or/* : unknown */(() => this/* : Main */.parseSymbolValue/* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Value]> */(state/* : CompileState */, input/* : string */)/* : Option<[CompileState, Value]> */)/* : unknown */.or/* : unknown */(() => this/* : Main */.parseInvokable/* : (arg0 : CompileState, arg1 : string, arg2 : number) => Option<[CompileState, Value]> */(state/* : CompileState */, input/* : string */, depth/* : number */)/* : Option<[CompileState, Value]> */)/* : unknown */.or/* : unknown */(() => this/* : Main */.parseDigits/* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Value]> */(state/* : CompileState */, input/* : string */)/* : Option<[CompileState, Value]> */)/* : unknown */.or/* : unknown */(() => this/* : Main */.parseInstanceOf/* : (arg0 : CompileState, arg1 : string, arg2 : number) => Option<[CompileState, Value]> */(state/* : CompileState */, input/* : string */, depth/* : number */)/* : Option<[CompileState, Value]> */)/* : unknown */.or/* : unknown */(() => this/* : Main */.parseOperation/* : (arg0 : CompileState, arg1 : string, arg2 : number, arg3 : Operator) => Option<[CompileState, Value]> */(state/* : CompileState */, input/* : string */, depth/* : number */, Operator/* : () => content-start new content-end */.ADD/* : unknown */)/* : Option<[CompileState, Value]> */)/* : unknown */.or/* : unknown */(() => this/* : Main */.parseOperation/* : (arg0 : CompileState, arg1 : string, arg2 : number, arg3 : Operator) => Option<[CompileState, Value]> */(state/* : CompileState */, input/* : string */, depth/* : number */, Operator/* : () => content-start new content-end */.EQUALS/* : unknown */)/* : Option<[CompileState, Value]> */)/* : unknown */.or/* : unknown */(() => this/* : Main */.parseOperation/* : (arg0 : CompileState, arg1 : string, arg2 : number, arg3 : Operator) => Option<[CompileState, Value]> */(state/* : CompileState */, input/* : string */, depth/* : number */, Operator/* : () => content-start new content-end */.SUBTRACT/* : unknown */)/* : Option<[CompileState, Value]> */)/* : unknown */.or/* : unknown */(() => this/* : Main */.parseOperation/* : (arg0 : CompileState, arg1 : string, arg2 : number, arg3 : Operator) => Option<[CompileState, Value]> */(state/* : CompileState */, input/* : string */, depth/* : number */, Operator/* : () => content-start new content-end */.AND/* : unknown */)/* : Option<[CompileState, Value]> */)/* : unknown */.or/* : unknown */(() => this/* : Main */.parseOperation/* : (arg0 : CompileState, arg1 : string, arg2 : number, arg3 : Operator) => Option<[CompileState, Value]> */(state/* : CompileState */, input/* : string */, depth/* : number */, Operator/* : () => content-start new content-end */.OR/* : unknown */)/* : Option<[CompileState, Value]> */)/* : unknown */.or/* : unknown */(() => this/* : Main */.parseOperation/* : (arg0 : CompileState, arg1 : string, arg2 : number, arg3 : Operator) => Option<[CompileState, Value]> */(state/* : CompileState */, input/* : string */, depth/* : number */, /*  Operator.GREATER_THAN_OR_EQUALS */)/* : Option<[CompileState, Value]> */)/* : unknown */.or/* : unknown */(() => this/* : Main */.parseOperation/* : (arg0 : CompileState, arg1 : string, arg2 : number, arg3 : Operator) => Option<[CompileState, Value]> */(state/* : CompileState */, input/* : string */, depth/* : number */, /*  Operator.LESS_THAN */)/* : Option<[CompileState, Value]> */)/* : unknown */.or/* : unknown */(() => this/* : Main */.parseNot/* : (arg0 : CompileState, arg1 : string, arg2 : number) => Option<[CompileState, Value]> */(state/* : CompileState */, input/* : string */, depth/* : number */)/* : Option<[CompileState, Value]> */)/* : unknown */.or/* : unknown */(() => this/* : Main */.parseMethodReference/* : (arg0 : CompileState, arg1 : string, arg2 : number) => Option<[CompileState, Value]> */(state/* : CompileState */, input/* : string */, depth/* : number */)/* : Option<[CompileState, Value]> */)/* : unknown */.or/* : unknown */(() => this/* : Main */.parseChar/* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Value]> */(state/* : CompileState */, input/* : string */)/* : Option<[CompileState, Value]> */)/* : unknown */.orElseGet/* : unknown */(() => [state/* : CompileState */, new Placeholder(input/* : string */)/* : Placeholder */])/* : unknown */;
	}
	parseChar(state : CompileState, input : string) : Option<[CompileState, Value]> {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */.startsWith/* : unknown */("'")/* : unknown */ && stripped/* : unknown */.endsWith/* : unknown */("'")/* : unknown */ && Strings/* : Strings */.length/* : (arg0 : string) => number */(stripped/* : unknown */)/* : number */ >= 2/* : number */){
			return new Some([state/* : CompileState */, new StringValue(stripped/* : unknown */.substring/* : unknown */(1/* : number */, Strings/* : Strings */.length/* : (arg0 : string) => number */(stripped/* : unknown */)/* : number */ - 1/* : number */)/* : unknown */)/* : StringValue */])/* : Some */;
		}
		return new None()/* : None */;
	}
	parseBoolean(state : CompileState, input : string) : Option<[CompileState, Value]> {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */ === "false"){
			return new Some([state/* : CompileState */, BooleanValue/* : BooleanValue */.False/* : unknown */])/* : Some */;
		}
		if (stripped/* : unknown */ === "true"){
			return new Some([state/* : CompileState */, BooleanValue/* : BooleanValue */.True/* : unknown */])/* : Some */;
		}
		return new None()/* : None */;
	}
	parseInstanceOf(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		return this/* : Main */.last/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(input/* : string */, "instanceof", (s, s2) => {
			let childTuple : [CompileState, Value] = this/* : Main */.parseValue/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state/* : CompileState */, s/* : unknown */, depth/* : number */)/* : [CompileState, Value] */;
			return this/* : Main */.parseDefinition/* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Definition]> */(childTuple/* : [CompileState, Value] */[0/* : number */], s2/* : unknown */)/* : Option<[CompileState, Definition]> */.map/* : (arg0 : (arg0 : [CompileState, Definition]) => R) => Option<R> */((definitionTuple : [CompileState, Definition]) => {
				let value = childTuple/* : [CompileState, Value] */[1/* : number */];
				let definition = definitionTuple/* : [CompileState, Definition] */[1/* : number */];
				let type = value/* : unknown */.type/* : unknown */()/* : unknown */;
				let variant : DataAccess = new DataAccess(value/* : unknown */, "_" + type.findName() + "Variant", Primitive/* : Primitive */.Unknown/* : unknown */)/* : DataAccess */;
				let generate = type/* : unknown */.findName/* : unknown */()/* : unknown */;
				let temp : SymbolValue = new SymbolValue(generate/* : unknown */ + "Variant." + definition/* : unknown */.findType/* : unknown */()/* : unknown */.findName/* : unknown */()/* : unknown */, Primitive/* : Primitive */.Unknown/* : unknown */)/* : SymbolValue */;
				let functionSegment : Statement = new Statement(depth/* : number */ + 1/* : number */, new Initialization(definition/* : unknown */, new Cast(value/* : unknown */, definition/* : unknown */.findType/* : unknown */()/* : unknown */)/* : Cast */)/* : Initialization */)/* : Statement */;
				return [definitionTuple/* : [CompileState, Definition] */[0/* : number */].addFunctionSegment/* : unknown */(functionSegment/* : Statement */)/* : unknown */.define/* : unknown */(definition/* : unknown */)/* : unknown */, new Operation(variant/* : DataAccess */, Operator/* : () => content-start new content-end */.EQUALS/* : unknown */, temp/* : SymbolValue */)/* : Operation */];
			})/* : Option<R> */;
		})/* : Option<T> */;
	}
	parseMethodReference(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		return this/* : Main */.last/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(input/* : string */, "::", (s, s2) => {
			let tuple : [CompileState, Value] = this/* : Main */.parseValue/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state/* : CompileState */, s/* : unknown */, depth/* : number */)/* : [CompileState, Value] */;
			return new Some([tuple/* : [CompileState, Value] */[0/* : number */], new DataAccess(tuple/* : [CompileState, Value] */[1/* : number */], s2/* : unknown */, Primitive/* : Primitive */.Unknown/* : unknown */)/* : DataAccess */])/* : Some */;
		})/* : Option<T> */;
	}
	parseNot(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */.startsWith/* : unknown */("!")/* : unknown */){
			let slice = stripped/* : unknown */.substring/* : unknown */(1/* : number */)/* : unknown */;
			let tuple : [CompileState, Value] = this/* : Main */.parseValue/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state/* : CompileState */, slice/* : unknown */, depth/* : number */)/* : [CompileState, Value] */;
			let value = tuple/* : [CompileState, Value] */[1/* : number */];
			return new Some([tuple/* : [CompileState, Value] */[0/* : number */], new Not(value/* : unknown */)/* : Not */])/* : Some */;
		}
		return new None()/* : None */;
	}
	parseLambda(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		return this/* : Main */.first/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(input/* : string */, "->", (beforeArrow, valueString) => {
			let strippedBeforeArrow = beforeArrow/* : unknown */.strip/* : unknown */()/* : unknown */;
			if (isSymbol/* : (arg0 : string) => boolean */(strippedBeforeArrow/* : unknown */)/* : boolean */){
				let type : Type = Primitive/* : Primitive */.Unknown/* : unknown */;
				if (/* state.typeRegister instanceof Some */(/* var expectedType */)/* : unknown */){
					if (/* expectedType */._UnknownVariant/* : unknown */ === UnknownVariant.FunctionType/* : unknown */){
						let functionType : FunctionType = /* expectedType */ as FunctionType;
						type/* : Type */ = functionType/* : FunctionType */.arguments/* : List<Type> */.get/* : (arg0 : number) => Option<Type> */(0/* : number */)/* : Option<Type> */.orElse/* : (arg0 : Type) => Type */(/* null */)/* : Type */;
					}
				}
				return this/* : Main */.assembleLambda/* : (arg0 : CompileState, arg1 : List<Definition>, arg2 : string, arg3 : number) => Some<[CompileState, Value]> */(state/* : CompileState */, Lists/* : Lists */.of/* : (arg0 : T[]) => List<T> */(ImmutableDefinition/* : ImmutableDefinition */.createSimpleDefinition/* : (arg0 : string, arg1 : Type) => Definition */(strippedBeforeArrow/* : unknown */, type/* : Type */)/* : Definition */)/* : List<T> */, valueString/* : unknown */, depth/* : number */)/* : Some<[CompileState, Value]> */;
			}
			if (strippedBeforeArrow/* : unknown */.startsWith/* : unknown */("(")/* : unknown */ && strippedBeforeArrow/* : unknown */.endsWith/* : unknown */(")")/* : unknown */){
				let parameterNames = this/* : Main */.divideAll/* : (arg0 : string, arg1 : (arg0 : DivideState, arg1 : string) => DivideState) => List<string> */(strippedBeforeArrow/* : unknown */.substring/* : unknown */(1/* : number */, Strings/* : Strings */.length/* : (arg0 : string) => number */(strippedBeforeArrow/* : unknown */)/* : number */ - 1/* : number */)/* : unknown */, this/* : Main */.foldValueChar/* : unknown */)/* : List<string> */.query/* : () => Query<string> */()/* : Query<string> */.map/* : (arg0 : (arg0 : string) => R) => Query<R> */(/* String */.strip/* : unknown */)/* : Query<R> */.filter/* : (arg0 : (arg0 : T) => boolean) => Option<T> */((value : T) => !value/* : T */.isEmpty/* : unknown */()/* : unknown */)/* : Option<T> */.map/* : (arg0 : (arg0 : T) => R) => Option<R> */((name : T) => ImmutableDefinition/* : ImmutableDefinition */.createSimpleDefinition/* : (arg0 : string, arg1 : Type) => Definition */(name/* : T */, Primitive/* : Primitive */.Unknown/* : unknown */)/* : Definition */)/* : Option<R> */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */;
				return this/* : Main */.assembleLambda/* : (arg0 : CompileState, arg1 : List<Definition>, arg2 : string, arg3 : number) => Some<[CompileState, Value]> */(state/* : CompileState */, parameterNames/* : unknown */, valueString/* : unknown */, depth/* : number */)/* : Some<[CompileState, Value]> */;
			}
			return new None()/* : None */;
		})/* : Option<T> */;
	}
	assembleLambda(state : CompileState, definitions : List<Definition>, valueString : string, depth : number) : Some<[CompileState, Value]> {
		let strippedValueString = valueString/* : string */.strip/* : unknown */()/* : unknown */;
		/* Tuple2Impl<CompileState, LambdaValue> value */;
		let state2 : CompileState = state/* : CompileState */.defineAll/* : (arg0 : List<Definition>) => CompileState */(definitions/* : List<Definition> */)/* : CompileState */;
		if (strippedValueString/* : unknown */.startsWith/* : unknown */("{")/* : unknown */ && strippedValueString/* : unknown */.endsWith/* : unknown */("}")/* : unknown */){
			let value1 : [CompileState, List<T>] = this/* : Main */.parseStatements/* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => [CompileState, T]) => [CompileState, List<T>] */(state2/* : CompileState */, strippedValueString/* : unknown */.substring/* : unknown */(1/* : number */, Strings/* : Strings */.length/* : (arg0 : string) => number */(strippedValueString/* : unknown */)/* : number */ - 1/* : number */)/* : unknown */, (state1, input1) => this/* : Main */.parseFunctionSegment/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, FunctionSegment] */(state1/* : unknown */, input1/* : unknown */, depth/* : number */ + 1/* : number */)/* : [CompileState, FunctionSegment] */)/* : [CompileState, List<T>] */;
			let right = value1/* : [CompileState, List<T>] */[1/* : number */];
			/* value */ = [value1/* : [CompileState, List<T>] */[0/* : number */], new BlockLambdaValue(depth/* : number */, right/* : unknown */)/* : BlockLambdaValue */];
		}
		else {
			let value1 : [CompileState, Value] = this/* : Main */.parseValue/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state2/* : CompileState */, strippedValueString/* : unknown */, depth/* : number */)/* : [CompileState, Value] */;
			/* value */ = [value1/* : [CompileState, List<T>] */[0/* : number */], value1/* : [CompileState, List<T>] */[1/* : number */]];
		}
		let right = /* value */.right/* : unknown */()/* : unknown */;
		return new Some([/* value */.left/* : unknown */()/* : unknown */, new Lambda(definitions/* : List<Definition> */, right/* : unknown */)/* : Lambda */])/* : Some */;
	}
	parseDigits(state : CompileState, input : string) : Option<[CompileState, Value]> {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (this/* : Main */.isNumber/* : (arg0 : string) => boolean */(stripped/* : unknown */)/* : boolean */){
			return new Some([state/* : CompileState */, new SymbolValue(stripped/* : unknown */, Primitive/* : Primitive */.Int/* : unknown */)/* : SymbolValue */])/* : Some */;
		}
		return new None()/* : None */;
	}
	isNumber(input : string) : boolean {
		/* String maybeTruncated */;
		if (input/* : string */.startsWith/* : unknown */("-")/* : unknown */){
			/* maybeTruncated */ = input/* : string */.substring/* : unknown */(1/* : number */)/* : unknown */;
		}
		else {
			/* maybeTruncated */ = input/* : string */;
		}
		return this/* : Main */.areAllDigits/* : (arg0 : string) => boolean */(/* maybeTruncated */)/* : boolean */;
	}
	areAllDigits(input : string) : boolean {
		/* for (var i = 0; i < Strings.length(input); i++) */{
			let c = input/* : string */.charAt/* : unknown */(/* i */)/* : unknown */;
			if (/* Character */.isDigit/* : unknown */(c/* : unknown */)/* : unknown */){
				/* continue */;
			}
			return false;
		}
		return true;
	}
	parseInvokable(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		return this/* : Main */.suffix/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(input/* : string */.strip/* : unknown */()/* : unknown */, ")", (withoutEnd : string) => {
			return this/* : Main */.split/* : (arg0 : () => Option<[string, string]>, arg1 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(() => this/* : Main */.toLast/* : (arg0 : string, arg1 : string, arg2 : (arg0 : DivideState, arg1 : string) => DivideState) => Option<[string, string]> */(withoutEnd/* : string */, "", this/* : Main */.foldInvocationStart/* : unknown */)/* : Option<[string, string]> */, (callerWithEnd, argumentsString) => {
				return this/* : Main */.suffix/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(callerWithEnd/* : unknown */, "(", (callerString : string) => {
					return this/* : Main */.assembleInvokable/* : (arg0 : CompileState, arg1 : number, arg2 : string, arg3 : string) => Some<[CompileState, Value]> */(state/* : CompileState */, depth/* : number */, argumentsString/* : unknown */, callerString/* : string */.strip/* : unknown */()/* : unknown */)/* : Some<[CompileState, Value]> */;
				})/* : Option<T> */;
			})/* : Option<T> */;
		})/* : Option<T> */;
	}
	assembleInvokable(state : CompileState, depth : number, argumentsString : string, callerString : string) : Some<[CompileState, Value]> {
		let callerTuple : [CompileState, Caller] = this/* : Main */.invocationHeader/* : (arg0 : CompileState, arg1 : number, arg2 : string) => [CompileState, Caller] */(state/* : CompileState */, depth/* : number */, callerString/* : string */)/* : [CompileState, Caller] */;
		let oldCallerState = callerTuple/* : [CompileState, Caller] */[0/* : number */];
		let oldCaller = callerTuple/* : [CompileState, Caller] */[1/* : number */];
		let newCaller : Caller = this/* : Main */.modifyCaller/* : (arg0 : CompileState, arg1 : Caller) => Caller */(oldCallerState/* : unknown */, oldCaller/* : unknown */)/* : Caller */;
		let callerType : FunctionType = this/* : Main */.findCallerType/* : (arg0 : Caller) => FunctionType */(newCaller/* : Caller */)/* : FunctionType */;
		let argumentsTuple : [CompileState, List<T>] = this/* : Main */.parseValuesWithIndices/* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : [number, string]) => Option<[CompileState, T]>) => Option<[CompileState, List<T>]> */(oldCallerState/* : unknown */, argumentsString/* : string */, (currentState, pair) => this/* : Main */.getTuple2Some/* : (arg0 : number, arg1 : CompileState, arg2 : [number, string], arg3 : FunctionType) => Some<[CompileState, Tuple2Impl<Argument, Type>]> */(depth/* : number */, currentState/* : unknown */, pair/* : unknown */, callerType/* : FunctionType */)/* : Some<[CompileState, Tuple2Impl<Argument, Type>]> */)/* : Option<[CompileState, List<T>]> */.orElseGet/* : (arg0 : () => [CompileState, List<T>]) => [CompileState, List<T>] */(() => [oldCallerState/* : unknown */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */])/* : [CompileState, List<T>] */;
		let argumentsState = argumentsTuple/* : [CompileState, List<T>] */[0/* : number */];
		let argumentsWithActualTypes = argumentsTuple/* : [CompileState, List<T>] */[1/* : number */];
		let arguments : List<Value> = this/* : Main */.retainValues/* : (arg0 : List<Argument>) => List<Value> */(argumentsWithActualTypes/* : unknown */.query/* : unknown */()/* : unknown */.map/* : unknown */(Tuple2/* : Tuple2 */.left/* : unknown */)/* : unknown */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */)/* : List<Value> */;
		if (newCaller/* : Caller */._CallerVariant/* : unknown */ === CallerVariant.ConstructionCaller/* : unknown */){
			if (constructionCaller/* : ConstructionCaller */.type/* : Type */.findName/* : () => string */()/* : string */ === "Tuple2Impl"){
			let constructionCaller : ConstructionCaller = newCaller/* : Caller */ as ConstructionCaller;
				return new Some([argumentsState/* : unknown */, new TupleNode(Lists/* : Lists */.of/* : (arg0 : T[]) => List<T> */(arguments/* : List<Value> */.get/* : (arg0 : number) => Option<Value> */(0/* : number */)/* : Option<Value> */.orElse/* : (arg0 : Value) => Value */(/* null */)/* : Value */, arguments/* : List<Value> */.get/* : (arg0 : number) => Option<Value> */(1/* : number */)/* : Option<Value> */.orElse/* : (arg0 : Value) => Value */(/* null */)/* : Value */)/* : List<T> */)/* : TupleNode */])/* : Some */;
			}
		}
		if (newCaller/* : Caller */._CallerVariant/* : unknown */ === CallerVariant.Value/* : unknown */){
			if (value/* : Value */._ValueVariant/* : unknown */ === ValueVariant.DataAccess/* : unknown */){
				let parent : Value = access/* : DataAccess */.parent/* : Value */;
				let property : string = access/* : DataAccess */.property/* : string */;
				let parentType : Type = parent/* : Value */.type/* : () => Type */()/* : Type */;
				if (/* parentType instanceof TupleType */){
					if (property/* : string */ === "left"){
			let value : Value = newCaller/* : Caller */ as Value;
				let access : DataAccess = value/* : Value */ as DataAccess;
						return new Some([argumentsState/* : unknown */, new IndexValue(parent/* : Value */, new SymbolValue("0", Primitive/* : Primitive */.Int/* : unknown */)/* : SymbolValue */)/* : IndexValue */])/* : Some */;
					}
					if (property/* : string */ === "right"){
						return new Some([argumentsState/* : unknown */, new IndexValue(parent/* : Value */, new SymbolValue("1", Primitive/* : Primitive */.Int/* : unknown */)/* : SymbolValue */)/* : IndexValue */])/* : Some */;
					}
				}
				if (property/* : string */ === "equals"){
					let first : Value = arguments/* : List<Value> */.get/* : (arg0 : number) => Option<Value> */(0/* : number */)/* : Option<Value> */.orElse/* : (arg0 : Value) => Value */(/* null */)/* : Value */;
					return new Some([argumentsState/* : unknown */, new Operation(parent/* : Value */, Operator/* : () => content-start new content-end */.EQUALS/* : unknown */, first/* : Value */)/* : Operation */])/* : Some */;
				}
			}
		}
		let invokable : Invokable = new Invokable(newCaller/* : Caller */, arguments/* : List<Value> */, callerType/* : FunctionType */.returns/* : Type */)/* : Invokable */;
		return new Some([argumentsState/* : unknown */, invokable/* : Invokable */])/* : Some */;
	}
	getTuple2Some(depth : number, currentState : CompileState, pair : [number, string], callerType : FunctionType) : Some<[CompileState, Tuple2Impl<Argument, Type>]> {
		let index = pair/* : [number, string] */[0/* : number */];
		let element = pair/* : [number, string] */[1/* : number */];
		let expectedType : Type = callerType/* : FunctionType */.arguments/* : List<Type> */.get/* : (arg0 : number) => Option<Type> */(index/* : unknown */)/* : Option<Type> */.orElse/* : (arg0 : Type) => Type */(Primitive/* : Primitive */.Unknown/* : unknown */)/* : Type */;
		let withExpected : CompileState = currentState/* : CompileState */.withExpectedType/* : (arg0 : Type) => CompileState */(expectedType/* : Type */)/* : CompileState */;
		let valueTuple : [CompileState, Argument] = this/* : Main */.parseArgument/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Argument] */(withExpected/* : CompileState */, element/* : unknown */, depth/* : number */)/* : [CompileState, Argument] */;
		let valueState = valueTuple/* : [CompileState, Argument] */[0/* : number */];
		let value = valueTuple/* : [CompileState, Argument] */[1/* : number */];
		let actualType = valueTuple/* : [CompileState, Argument] */[0/* : number */].typeRegister/* : unknown */.orElse/* : unknown */(Primitive/* : Primitive */.Unknown/* : unknown */)/* : unknown */;
		return new Some([valueState/* : unknown */, [value/* : unknown */, actualType/* : unknown */]])/* : Some */;
	}
	retainValues(arguments : List<Argument>) : List<Value> {
		return arguments/* : List<Argument> */.query/* : () => Query<Argument> */()/* : Query<Argument> */.map/* : (arg0 : (arg0 : Argument) => R) => Query<R> */(this/* : Main */.retainValue/* : unknown */)/* : Query<R> */.flatMap/* : (arg0 : (arg0 : T) => Option<R>) => Option<R> */(Queries/* : Queries */.fromOption/* : unknown */)/* : Option<R> */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */;
	}
	retainValue(argument : Argument) : Option<Value> {
		if (argument/* : Argument */._ArgumentVariant/* : unknown */ === ArgumentVariant.Value/* : unknown */){
			let value : Value = argument/* : Argument */ as Value;
			return new Some(value/* : Value */)/* : Some */;
		}
		return new None()/* : None */;
	}
	parseArgument(state : CompileState, element : string, depth : number) : [CompileState, Argument] {
		if (element/* : string */.isEmpty/* : unknown */()/* : unknown */){
			return [state/* : CompileState */, new Whitespace()/* : Whitespace */];
		}
		let tuple : [CompileState, Value] = this/* : Main */.parseValue/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state/* : CompileState */, element/* : string */, depth/* : number */)/* : [CompileState, Value] */;
		return [tuple/* : [CompileState, Value] */[0/* : number */], tuple/* : [CompileState, Value] */[1/* : number */]];
	}
	findCallerType(newCaller : Caller) : FunctionType {
		let callerType : FunctionType = new FunctionType(Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, Primitive/* : Primitive */.Unknown/* : unknown */)/* : FunctionType */;
		/* switch (newCaller) */{
			/* case ConstructionCaller constructionCaller -> */{
				callerType/* : FunctionType */ = /* constructionCaller */.toFunction/* : unknown */()/* : unknown */;
			}
			/* case Value value -> */{
				let type = /* value */.type/* : unknown */()/* : unknown */;
				if (type/* : unknown */._UnknownVariant/* : unknown */ === UnknownVariant.FunctionType/* : unknown */){
					let functionType : FunctionType = type/* : unknown */ as FunctionType;
					callerType/* : FunctionType */ = functionType/* : FunctionType */;
				}
			}
		}
		return callerType/* : FunctionType */;
	}
	modifyCaller(state : CompileState, oldCaller : Caller) : Caller {
		if (oldCaller/* : Caller */._CallerVariant/* : unknown */ === CallerVariant.DataAccess/* : unknown */){
			let type : Type = this/* : Main */.resolveType/* : (arg0 : Value, arg1 : CompileState) => Type */(access/* : DataAccess */.parent/* : Value */, state/* : CompileState */)/* : Type */;
			if (/* type instanceof FunctionType */){
			let access : DataAccess = oldCaller/* : Caller */ as DataAccess;
				return access/* : DataAccess */.parent/* : Value */;
			}
		}
		return oldCaller/* : Caller */;
	}
	resolveType(value : Value, state : CompileState) : Type {
		return value/* : Value */.type/* : () => Type */()/* : Type */;
	}
	invocationHeader(state : CompileState, depth : number, callerString1 : string) : [CompileState, Caller] {
		if (callerString1/* : string */.startsWith/* : unknown */("new ")/* : unknown */){
			let input1 : string = callerString1/* : string */.substring/* : unknown */(Strings/* : Strings */.length/* : (arg0 : string) => number */("new ")/* : number */)/* : unknown */;
			let map : Option<R> = this/* : Main */.parseType/* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Type]> */(state/* : CompileState */, input1/* : string */)/* : Option<[CompileState, Type]> */.map/* : (arg0 : (arg0 : [CompileState, Type]) => R) => Option<R> */((type : [CompileState, Type]) => {
				let right = type/* : [CompileState, Type] */[1/* : number */];
				return [type/* : [CompileState, Type] */[0/* : number */], new ConstructionCaller(right/* : unknown */)/* : ConstructionCaller */];
			})/* : Option<R> */;
			if (map/* : Option<R> */.isPresent/* : unknown */()/* : unknown */){
				return map/* : Option<R> */.orElse/* : unknown */(/* null */)/* : unknown */;
			}
		}
		let tuple : [CompileState, Value] = this/* : Main */.parseValue/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state/* : CompileState */, callerString1/* : string */, depth/* : number */)/* : [CompileState, Value] */;
		return [tuple/* : [CompileState, Value] */[0/* : number */], tuple/* : [CompileState, Value] */[1/* : number */]];
	}
	foldInvocationStart(state : DivideState, c : string) : DivideState {
		let appended : DivideState = state/* : DivideState */.append/* : (arg0 : string) => DivideState */(c/* : string */)/* : DivideState */;
		if (c/* : string */ === "("){
			let enter = appended/* : DivideState */.enter/* : unknown */()/* : unknown */;
			if (enter/* : unknown */.isShallow/* : unknown */()/* : unknown */){
				return enter/* : unknown */.advance/* : unknown */()/* : unknown */;
			}
			return enter/* : unknown */;
		}
		if (c/* : string */ === ")"){
			return appended/* : DivideState */.exit/* : unknown */()/* : unknown */;
		}
		return appended/* : DivideState */;
	}
	parseDataAccess(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		return this/* : Main */.last/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(input/* : string */.strip/* : unknown */()/* : unknown */, ".", (parentString, rawProperty) => {
			let property = rawProperty/* : unknown */.strip/* : unknown */()/* : unknown */;
			if (!isSymbol/* : (arg0 : string) => boolean */(property/* : unknown */)/* : unknown */){
				return new None()/* : None */;
			}
			let tuple : [CompileState, Value] = this/* : Main */.parseValue/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state/* : CompileState */, parentString/* : unknown */, depth/* : number */)/* : [CompileState, Value] */;
			let parent = tuple/* : [CompileState, Value] */[1/* : number */];
			let parentType = parent/* : unknown */.type/* : unknown */()/* : unknown */;
			let type : Type = Primitive/* : Primitive */.Unknown/* : unknown */;
			if (parentType/* : unknown */._UnknownVariant/* : unknown */ === UnknownVariant.FindableType/* : unknown */){
				if (/* objectType.find(property) instanceof Some */(/* var memberType */)/* : unknown */){
				let objectType : FindableType = parentType/* : unknown */ as FindableType;
					type/* : Type */ = /* memberType */;
				}
			}
			return new Some([tuple/* : [CompileState, Value] */[0/* : number */], new DataAccess(parent/* : unknown */, property/* : unknown */, type/* : Type */)/* : DataAccess */])/* : Some */;
		})/* : Option<T> */;
	}
	parseString(state : CompileState, input : string) : Option<[CompileState, Value]> {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */.startsWith/* : unknown */("\"")/* : unknown */ && stripped/* : unknown */.endsWith/* : unknown */("\"")/* : unknown */){
			return new Some([state/* : CompileState */, new StringValue(stripped/* : unknown */.substring/* : unknown */(1/* : number */, Strings/* : Strings */.length/* : (arg0 : string) => number */(stripped/* : unknown */)/* : number */ - 1/* : number */)/* : unknown */)/* : StringValue */])/* : Some */;
		}
		return new None()/* : None */;
	}
	parseSymbolValue(state : CompileState, value : string) : Option<[CompileState, Value]> {
		let stripped = value/* : string */.strip/* : unknown */()/* : unknown */;
		if (isSymbol/* : (arg0 : string) => boolean */(stripped/* : unknown */)/* : boolean */){
			if (/* state.resolveValue(stripped) instanceof Some */(/* var type */)/* : unknown */){
				return new Some([state/* : CompileState */, new SymbolValue(stripped/* : unknown */, type/* : () => Type */)/* : SymbolValue */])/* : Some */;
			}
			if (/* state.resolveType(stripped) instanceof Some */(/* var type */)/* : unknown */){
				return new Some([state/* : CompileState */, new SymbolValue(stripped/* : unknown */, type/* : () => Type */)/* : SymbolValue */])/* : Some */;
			}
			return new Some([state/* : CompileState */, new Placeholder(stripped/* : unknown */)/* : Placeholder */])/* : Some */;
		}
		return new None()/* : None */;
	}
	parseOperation(state : CompileState, value : string, depth : number, operator : Operator) : Option<[CompileState, Value]> {
		return this/* : Main */.first/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(value/* : string */, operator/* : Operator */.sourceRepresentation/* : string */, (leftString, rightString) => {
			let leftTuple : [CompileState, Value] = this/* : Main */.parseValue/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state/* : CompileState */, leftString/* : unknown */, depth/* : number */)/* : [CompileState, Value] */;
			let rightTuple : [CompileState, Value] = this/* : Main */.parseValue/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(leftTuple/* : [CompileState, Value] */[0/* : number */], rightString/* : unknown */, depth/* : number */)/* : [CompileState, Value] */;
			let left = leftTuple/* : [CompileState, Value] */[1/* : number */];
			let right = rightTuple/* : [CompileState, Value] */[1/* : number */];
			return new Some([rightTuple/* : [CompileState, Value] */[0/* : number */], new Operation(left/* : unknown */, operator/* : Operator */, right/* : unknown */)/* : Operation */])/* : Some */;
		})/* : Option<T> */;
	}
	parseValuesOrEmpty<T>(state : CompileState, input : string, mapper : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) : [CompileState, List<T>] {
		return this/* : Main */.parseValues/* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) => Option<[CompileState, List<T>]> */(state/* : CompileState */, input/* : string */, mapper/* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]> */)/* : Option<[CompileState, List<T>]> */.orElseGet/* : (arg0 : () => [CompileState, List<T>]) => [CompileState, List<T>] */(() => [state/* : CompileState */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */])/* : [CompileState, List<T>] */;
	}
	parseValues<T>(state : CompileState, input : string, mapper : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) : Option<[CompileState, List<T>]> {
		return this/* : Main */.parseValuesWithIndices/* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : [number, string]) => Option<[CompileState, T]>) => Option<[CompileState, List<T>]> */(state/* : CompileState */, input/* : string */, (state1, tuple) => mapper/* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]> */(state1/* : unknown */, tuple/* : unknown */.right/* : unknown */()/* : unknown */)/* : Option<[CompileState, T]> */)/* : Option<[CompileState, List<T>]> */;
	}
	parseValuesWithIndices<T>(state : CompileState, input : string, mapper : (arg0 : CompileState, arg1 : [number, string]) => Option<[CompileState, T]>) : Option<[CompileState, List<T>]> {
		return this/* : Main */.parseAllWithIndices/* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : DivideState, arg1 : string) => DivideState, arg3 : (arg0 : CompileState, arg1 : [number, string]) => Option<[CompileState, T]>) => Option<[CompileState, List<T>]> */(state/* : CompileState */, input/* : string */, this/* : Main */.foldValueChar/* : unknown */, mapper/* : (arg0 : CompileState, arg1 : [number, string]) => Option<[CompileState, T]> */)/* : Option<[CompileState, List<T>]> */;
	}
	parseParameter(state : CompileState, input : string) : [CompileState, Parameter] {
		if (Strings/* : Strings */.isBlank/* : (arg0 : string) => boolean */(input/* : string */)/* : boolean */){
			return [state/* : CompileState */, new Whitespace()/* : Whitespace */];
		}
		return this/* : Main */.parseDefinition/* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Definition]> */(state/* : CompileState */, input/* : string */)/* : Option<[CompileState, Definition]> */.map/* : (arg0 : (arg0 : [CompileState, Definition]) => R) => Option<R> */((tuple : [CompileState, Definition]) => [tuple/* : [CompileState, Definition] */[0/* : number */], tuple/* : [CompileState, Definition] */[1/* : number */]])/* : Option<R> */.orElseGet/* : unknown */(() => [state/* : CompileState */, new Placeholder(input/* : string */)/* : Placeholder */])/* : unknown */;
	}
	parseField(input : string, depth : number, state : CompileState) : Option<[CompileState, IncompleteClassSegment]> {
		return this/* : Main */.suffix/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(input/* : string */.strip/* : unknown */()/* : unknown */, ";", (withoutEnd : string) => {
			return this/* : Main */.parseClassInitialization/* : (arg0 : number, arg1 : CompileState, arg2 : string) => Option<[CompileState, IncompleteClassSegment]> */(depth/* : number */, state/* : CompileState */, withoutEnd/* : string */)/* : Option<[CompileState, IncompleteClassSegment]> */.or/* : (arg0 : () => Option<[CompileState, IncompleteClassSegment]>) => Option<[CompileState, IncompleteClassSegment]> */(() => {
				return this/* : Main */.parseClassDefinition/* : (arg0 : number, arg1 : CompileState, arg2 : string) => Option<[CompileState, IncompleteClassSegment]> */(depth/* : number */, state/* : CompileState */, withoutEnd/* : string */)/* : Option<[CompileState, IncompleteClassSegment]> */;
			})/* : Option<[CompileState, IncompleteClassSegment]> */;
		})/* : Option<T> */;
	}
	parseClassDefinition(depth : number, state : CompileState, withoutEnd : string) : Option<[CompileState, IncompleteClassSegment]> {
		return this/* : Main */.parseDefinition/* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Definition]> */(state/* : CompileState */, withoutEnd/* : string */)/* : Option<[CompileState, Definition]> */.map/* : (arg0 : (arg0 : [CompileState, Definition]) => R) => Option<R> */((result : [CompileState, Definition]) => {
			return [result/* : [CompileState, Definition] */[0/* : number */], new ClassDefinition(depth/* : number */, result/* : [CompileState, Definition] */[1/* : number */])/* : ClassDefinition */];
		})/* : Option<R> */;
	}
	parseClassInitialization(depth : number, state : CompileState, withoutEnd : string) : Option<[CompileState, IncompleteClassSegment]> {
		return this/* : Main */.first/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(withoutEnd/* : string */, "=", (s, s2) => {
			return this/* : Main */.parseDefinition/* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Definition]> */(state/* : CompileState */, s/* : unknown */)/* : Option<[CompileState, Definition]> */.map/* : (arg0 : (arg0 : [CompileState, Definition]) => R) => Option<R> */((result : [CompileState, Definition]) => {
				let valueTuple : [CompileState, Value] = this/* : Main */.parseValue/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(result/* : [CompileState, Definition] */[0/* : number */], s2/* : unknown */, depth/* : number */)/* : [CompileState, Value] */;
				return [valueTuple/* : [CompileState, Value] */[0/* : number */], new ClassInitialization(depth/* : number */, result/* : [CompileState, Definition] */[1/* : number */], valueTuple/* : [CompileState, Value] */[1/* : number */])/* : ClassInitialization */];
			})/* : Option<R> */;
		})/* : Option<T> */;
	}
	parseDefinition(state : CompileState, input : string) : Option<[CompileState, Definition]> {
		return this/* : Main */.last/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(input/* : string */.strip/* : unknown */()/* : unknown */, " ", (beforeName, name) => {
			return this/* : Main */.split/* : (arg0 : () => Option<[string, string]>, arg1 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(() => this/* : Main */.toLast/* : (arg0 : string, arg1 : string, arg2 : (arg0 : DivideState, arg1 : string) => DivideState) => Option<[string, string]> */(beforeName/* : unknown */, " ", this/* : Main */.foldTypeSeparator/* : unknown */)/* : Option<[string, string]> */, (beforeType, type) => {
				return this/* : Main */.last/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(beforeType/* : unknown */, "\n", (s, s2) => {
					let annotations : List<string> = this/* : Main */.parseAnnotations/* : (arg0 : string) => List<string> */(s/* : unknown */)/* : List<string> */;
					return this/* : Main */.getOr/* : (arg0 : CompileState, arg1 : string, arg2 : string, arg3 : string, arg4 : List<string>) => Option<[CompileState, Definition]> */(state/* : CompileState */, name/* : unknown */, s2/* : unknown */, type/* : unknown */, annotations/* : List<string> */)/* : Option<[CompileState, Definition]> */;
				})/* : Option<T> */.or/* : (arg0 : () => Option<T>) => Option<T> */(() => {
					return this/* : Main */.getOr/* : (arg0 : CompileState, arg1 : string, arg2 : string, arg3 : string, arg4 : List<string>) => Option<[CompileState, Definition]> */(state/* : CompileState */, name/* : unknown */, beforeType/* : unknown */, type/* : unknown */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */)/* : Option<[CompileState, Definition]> */;
				})/* : Option<T> */;
			})/* : Option<T> */.or/* : (arg0 : () => Option<T>) => Option<T> */(() => this/* : Main */.assembleDefinition/* : (arg0 : CompileState, arg1 : List<string>, arg2 : List<string>, arg3 : string, arg4 : List<string>, arg5 : string) => Option<[CompileState, Definition]> */(state/* : CompileState */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, name/* : unknown */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, beforeName/* : unknown */)/* : Option<[CompileState, Definition]> */)/* : Option<T> */;
		})/* : Option<T> */;
	}
	getOr(state : CompileState, name : string, beforeType : string, type : string, annotations : List<string>) : Option<[CompileState, Definition]> {
		return this/* : Main */.suffix/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(beforeType/* : string */.strip/* : unknown */()/* : unknown */, ">", (withoutTypeParamStart : string) => {
			return this/* : Main */.first/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(withoutTypeParamStart/* : string */, "<", (beforeTypeParams, typeParamsString) => {
				let typeParams : [CompileState, List<T>] = this/* : Main */.parseValuesOrEmpty/* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) => [CompileState, List<T>] */(state/* : CompileState */, typeParamsString/* : unknown */, (state1, s) => new Some([state1/* : unknown */, s/* : unknown */.strip/* : unknown */()/* : unknown */])/* : Some */)/* : [CompileState, List<T>] */;
				return this/* : Main */.assembleDefinition/* : (arg0 : CompileState, arg1 : List<string>, arg2 : List<string>, arg3 : string, arg4 : List<string>, arg5 : string) => Option<[CompileState, Definition]> */(typeParams/* : [CompileState, List<T>] */[0/* : number */], annotations/* : List<string> */, this/* : Main */.parseModifiers/* : (arg0 : string) => List<string> */(beforeTypeParams/* : unknown */)/* : List<string> */, name/* : string */, typeParams/* : [CompileState, List<T>] */[1/* : number */], type/* : string */)/* : Option<[CompileState, Definition]> */;
			})/* : Option<T> */;
		})/* : Option<T> */.or/* : (arg0 : () => Option<T>) => Option<T> */(() => {
			return this/* : Main */.assembleDefinition/* : (arg0 : CompileState, arg1 : List<string>, arg2 : List<string>, arg3 : string, arg4 : List<string>, arg5 : string) => Option<[CompileState, Definition]> */(state/* : CompileState */, annotations/* : List<string> */, this/* : Main */.parseModifiers/* : (arg0 : string) => List<string> */(beforeType/* : string */)/* : List<string> */, name/* : string */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, type/* : string */)/* : Option<[CompileState, Definition]> */;
		})/* : Option<T> */;
	}
	parseModifiers(modifiers : string) : List<string> {
		return this/* : Main */.divideAll/* : (arg0 : string, arg1 : (arg0 : DivideState, arg1 : string) => DivideState) => List<string> */(modifiers/* : string */.strip/* : unknown */()/* : unknown */, (state1, c) => this/* : Main */.foldByDelimiter/* : (arg0 : DivideState, arg1 : string, arg2 : string) => DivideState */(state1/* : unknown */, c/* : unknown */, " ")/* : DivideState */)/* : List<string> */.query/* : () => Query<string> */()/* : Query<string> */.map/* : (arg0 : (arg0 : string) => R) => Query<R> */(/* String */.strip/* : unknown */)/* : Query<R> */.filter/* : (arg0 : (arg0 : T) => boolean) => Option<T> */((value : T) => !value/* : T */.isEmpty/* : unknown */()/* : unknown */)/* : Option<T> */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */;
	}
	toLast(input : string, separator : string, folder : (arg0 : DivideState, arg1 : string) => DivideState) : Option<[string, string]> {
		let divisions : List<string> = this/* : Main */.divideAll/* : (arg0 : string, arg1 : (arg0 : DivideState, arg1 : string) => DivideState) => List<string> */(input/* : string */, folder/* : (arg0 : DivideState, arg1 : string) => DivideState */)/* : List<string> */;
		return divisions/* : List<string> */.removeLast/* : () => Option<[List<T>, T]> */()/* : Option<[List<T>, T]> */.map/* : (arg0 : (arg0 : [List<T>, T]) => R) => Option<R> */((removed : [List<T>, T]) => {
			let left = removed/* : [List<T>, T] */[0/* : number */].query/* : unknown */()/* : unknown */.collect/* : unknown */(new Joiner(separator/* : string */)/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
			let right = removed/* : [List<T>, T] */[1/* : number */];
			return [left/* : unknown */, right/* : unknown */];
		})/* : Option<R> */;
	}
	foldTypeSeparator(state : DivideState, c : string) : DivideState {
		if (c/* : string */ === " " && state/* : DivideState */.isLevel/* : unknown */()/* : unknown */){
			return state/* : DivideState */.advance/* : () => DivideState */()/* : DivideState */;
		}
		let appended : DivideState = state/* : DivideState */.append/* : (arg0 : string) => DivideState */(c/* : string */)/* : DivideState */;
		if (c/* : string */ === /*  ' */ < /* ' */){
			return appended/* : DivideState */.enter/* : unknown */()/* : unknown */;
		}
		if (c/* : string */ === ">"){
			return appended/* : DivideState */.exit/* : unknown */()/* : unknown */;
		}
		return appended/* : DivideState */;
	}
	assembleDefinition(state : CompileState, annotations : List<string>, modifiers : List<string>, rawName : string, typeParams : List<string>, type : string) : Option<[CompileState, Definition]> {
		return this/* : Main */.parseType/* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Type]> */(state/* : CompileState */.withTypeParams/* : (arg0 : List<string>) => CompileState */(typeParams/* : List<string> */)/* : CompileState */, type/* : string */)/* : Option<[CompileState, Type]> */.flatMap/* : (arg0 : (arg0 : [CompileState, Type]) => Option<R>) => Option<R> */((type1 : [CompileState, Type]) => {
			let stripped = rawName/* : string */.strip/* : unknown */()/* : unknown */;
			if (!isSymbol/* : (arg0 : string) => boolean */(stripped/* : unknown */)/* : unknown */){
				return new None()/* : None */;
			}
			let newModifiers = modifiers/* : List<string> */.query/* : () => Query<string> */()/* : Query<string> */.filter/* : (arg0 : (arg0 : string) => boolean) => Query<string> */((value : string) => !this/* : Main */.isAccessor/* : unknown */(value/* : string */)/* : unknown */)/* : Query<string> */.map/* : (arg0 : (arg0 : T) => R) => Option<R> */((modifier : T) => /* modifier.equals("final") ? "readonly" : modifier */)/* : Option<R> */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */;
			let node : ImmutableDefinition = new ImmutableDefinition(annotations/* : List<string> */, newModifiers/* : unknown */, stripped/* : unknown */, type1/* : [CompileState, Type] */[1/* : number */], typeParams/* : List<string> */)/* : ImmutableDefinition */;
			return new Some([type1/* : [CompileState, Type] */[0/* : number */], node/* : ImmutableDefinition */])/* : Some */;
		})/* : Option<R> */;
	}
	isAccessor(value : string) : boolean {
		return value/* : string */ === "private";
	}
	foldValueChar(state : DivideState, c : string) : DivideState {
		if (c/* : string */ === "," && state/* : DivideState */.isLevel/* : unknown */()/* : unknown */){
			return state/* : DivideState */.advance/* : () => DivideState */()/* : DivideState */;
		}
		let appended : DivideState = state/* : DivideState */.append/* : (arg0 : string) => DivideState */(c/* : string */)/* : DivideState */;
		if (c/* : string */ === /*  ' */ - /* ' */){
			let peeked = appended/* : DivideState */.peek/* : unknown */()/* : unknown */;
			if (peeked/* : unknown */ === ">"){
				return appended/* : DivideState */.popAndAppendToOption/* : unknown */()/* : unknown */.orElse/* : unknown */(appended/* : DivideState */)/* : unknown */;
			}
			else {
				return appended/* : DivideState */;
			}
		}
		if (c/* : string */ === /*  ' */ < /* '  */ || c/* : string */ === "(" || c/* : string */ === "{"){
			return appended/* : DivideState */.enter/* : unknown */()/* : unknown */;
		}
		if (c/* : string */ === ">" || c/* : string */ === ")" || c/* : string */ === "}"){
			return appended/* : DivideState */.exit/* : unknown */()/* : unknown */;
		}
		return appended/* : DivideState */;
	}
	parseType(state : CompileState, input : string) : Option<[CompileState, Type]> {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */ === "int" || stripped/* : unknown */ === "Integer"){
			return new Some([state/* : CompileState */, Primitive/* : Primitive */.Int/* : unknown */])/* : Some */;
		}
		if (stripped/* : unknown */ === "String" || stripped/* : unknown */ === "char" || stripped/* : unknown */ === "Character"){
			return new Some([state/* : CompileState */, Primitive/* : Primitive */.String/* : unknown */])/* : Some */;
		}
		if (stripped/* : unknown */ === "var"){
			return new Some([state/* : CompileState */, Primitive/* : Primitive */.Unknown/* : unknown */])/* : Some */;
		}
		if (stripped/* : unknown */ === "boolean"){
			return new Some([state/* : CompileState */, Primitive/* : Primitive */.Boolean/* : unknown */])/* : Some */;
		}
		if (stripped/* : unknown */ === "void"){
			return new Some([state/* : CompileState */, Primitive/* : Primitive */.Void/* : unknown */])/* : Some */;
		}
		if (isSymbol/* : (arg0 : string) => boolean */(stripped/* : unknown */)/* : boolean */){
			if (/* state.resolveType(stripped) instanceof Some */(/* var resolved */)/* : unknown */){
				return new Some([state/* : CompileState */, /* resolved */])/* : Some */;
			}
			else {
				return new Some([state/* : CompileState */, new Placeholder(stripped/* : unknown */)/* : Placeholder */])/* : Some */;
			}
		}
		return this/* : Main */.parseTemplate/* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Type]> */(state/* : CompileState */, input/* : string */)/* : Option<[CompileState, Type]> */.or/* : (arg0 : () => Option<[CompileState, Type]>) => Option<[CompileState, Type]> */(() => this/* : Main */.varArgs/* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Type]> */(state/* : CompileState */, input/* : string */)/* : Option<[CompileState, Type]> */)/* : Option<[CompileState, Type]> */;
	}
	varArgs(state : CompileState, input : string) : Option<[CompileState, Type]> {
		return this/* : Main */.suffix/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(input/* : string */, "...", (s : string) => {
			return this/* : Main */.parseType/* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Type]> */(state/* : CompileState */, s/* : string */)/* : Option<[CompileState, Type]> */.map/* : (arg0 : (arg0 : [CompileState, Type]) => R) => Option<R> */((inner : [CompileState, Type]) => {
				let newState = inner/* : [CompileState, Type] */[0/* : number */];
				let child = inner/* : [CompileState, Type] */[1/* : number */];
				return [newState/* : unknown */, new ArrayType(child/* : unknown */)/* : ArrayType */];
			})/* : Option<R> */;
		})/* : Option<T> */;
	}
	assembleTemplate(base : string, state : CompileState, arguments : List<Argument>) : [CompileState, Type] {
		let children = arguments/* : List<Argument> */.query/* : () => Query<Argument> */()/* : Query<Argument> */.map/* : (arg0 : (arg0 : Argument) => R) => Query<R> */(this/* : Main */.retainType/* : unknown */)/* : Query<R> */.flatMap/* : (arg0 : (arg0 : T) => Option<R>) => Option<R> */(Queries/* : Queries */.fromOption/* : unknown */)/* : Option<R> */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */;
		if (base/* : string */ === "BiFunction"){
			return [state/* : CompileState */, new FunctionType(Lists/* : Lists */.of/* : (arg0 : T[]) => List<T> */(children/* : unknown */.get/* : unknown */(0/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */, children/* : unknown */.get/* : unknown */(1/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */)/* : List<T> */, children/* : unknown */.get/* : unknown */(2/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */)/* : FunctionType */];
		}
		if (base/* : string */ === "Function"){
			return [state/* : CompileState */, new FunctionType(Lists/* : Lists */.of/* : (arg0 : T[]) => List<T> */(children/* : unknown */.get/* : unknown */(0/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */)/* : List<T> */, children/* : unknown */.get/* : unknown */(1/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */)/* : FunctionType */];
		}
		if (base/* : string */ === "Predicate"){
			return [state/* : CompileState */, new FunctionType(Lists/* : Lists */.of/* : (arg0 : T[]) => List<T> */(children/* : unknown */.get/* : unknown */(0/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */)/* : List<T> */, Primitive/* : Primitive */.Boolean/* : unknown */)/* : FunctionType */];
		}
		if (base/* : string */ === "Supplier"){
			return [state/* : CompileState */, new FunctionType(Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, children/* : unknown */.get/* : unknown */(0/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */)/* : FunctionType */];
		}
		if (base/* : string */ === "Consumer"){
			return [state/* : CompileState */, new FunctionType(Lists/* : Lists */.of/* : (arg0 : T[]) => List<T> */(children/* : unknown */.get/* : unknown */(0/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */)/* : List<T> */, Primitive/* : Primitive */.Void/* : unknown */)/* : FunctionType */];
		}
		if (base/* : string */ === "Tuple2" && children/* : unknown */.size/* : unknown */()/* : unknown */ >= 2/* : number */){
			return [state/* : CompileState */, new TupleType(children/* : unknown */)/* : TupleType */];
		}
		if (state/* : CompileState */.resolveType/* : (arg0 : string) => Option<Type> */(base/* : string */)/* : Option<Type> */._OptionVariant/* : unknown */ === OptionVariant.Some/* : unknown */){
			let baseType : Type = some/* : Some<Type> */.value/* : Type */;
			if (baseType/* : Type */._TypeVariant/* : unknown */ === TypeVariant.ObjectType/* : unknown */){
			let some : Some<Type> = state/* : CompileState */.resolveType/* : (arg0 : string) => Option<Type> */(base/* : string */)/* : Option<Type> */ as Some<Type>;
				let findableType : ObjectType = baseType/* : Type */ as ObjectType;
				return [state/* : CompileState */, new Template(findableType/* : ObjectType */, children/* : unknown */)/* : Template */];
			}
		}
		return [state/* : CompileState */, new Template(new ObjectType(base/* : string */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */)/* : ObjectType */, children/* : unknown */)/* : Template */];
	}
	parseTemplate(state : CompileState, input : string) : Option<[CompileState, Type]> {
		return this/* : Main */.suffix/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(input/* : string */.strip/* : unknown */()/* : unknown */, ">", (withoutEnd : string) => {
			return this/* : Main */.first/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(withoutEnd/* : string */, "<", (base, argumentsString) => {
				let strippedBase = base/* : unknown */.strip/* : unknown */()/* : unknown */;
				return this/* : Main */.parseValues/* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) => Option<[CompileState, List<T>]> */(state/* : CompileState */, argumentsString/* : unknown */, this/* : Main */.parseArgument/* : unknown */)/* : Option<[CompileState, List<T>]> */.map/* : (arg0 : (arg0 : [CompileState, List<T>]) => R) => Option<R> */((argumentsTuple : [CompileState, List<T>]) => {
					return this/* : Main */.assembleTemplate/* : (arg0 : string, arg1 : CompileState, arg2 : List<Argument>) => [CompileState, Type] */(strippedBase/* : unknown */, argumentsTuple/* : [CompileState, List<T>] */[0/* : number */], argumentsTuple/* : [CompileState, List<T>] */[1/* : number */])/* : [CompileState, Type] */;
				})/* : Option<R> */;
			})/* : Option<T> */;
		})/* : Option<T> */;
	}
	retainType(argument : Argument) : Option<Type> {
		if (argument/* : Argument */._ArgumentVariant/* : unknown */ === ArgumentVariant.Type/* : unknown */){
			let type : Type = argument/* : Argument */ as Type;
			return new Some(type/* : Type */)/* : Some */;
		}
		else {
			return new None<Type>()/* : None<Type> */;
		}
	}
	parseArgument(state : CompileState, input : string) : Option<[CompileState, Argument]> {
		if (Strings/* : Strings */.isBlank/* : (arg0 : string) => boolean */(input/* : string */)/* : boolean */){
			return new Some([state/* : CompileState */, new Whitespace()/* : Whitespace */])/* : Some */;
		}
		return this/* : Main */.parseType/* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Type]> */(state/* : CompileState */, input/* : string */)/* : Option<[CompileState, Type]> */.map/* : (arg0 : (arg0 : [CompileState, Type]) => R) => Option<R> */((tuple : [CompileState, Type]) => [tuple/* : [CompileState, Type] */[0/* : number */], tuple/* : [CompileState, Type] */[1/* : number */]])/* : Option<R> */;
	}
	last<T>(input : string, infix : string, mapper : (arg0 : string, arg1 : string) => Option<T>) : Option<T> {
		return this/* : Main */.infix/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<number>, arg3 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(input/* : string */, infix/* : string */, this/* : Main */.findLast/* : unknown */, mapper/* : (arg0 : string, arg1 : string) => Option<T> */)/* : Option<T> */;
	}
	findLast(input : string, infix : string) : Option<number> {
		let index = input/* : string */.lastIndexOf/* : unknown */(infix/* : string */)/* : unknown */;
		if (index/* : unknown */ === -1/* : number */){
			return new None<number>()/* : None<number> */;
		}
		return new Some(index/* : unknown */)/* : Some */;
	}
	first<T>(input : string, infix : string, mapper : (arg0 : string, arg1 : string) => Option<T>) : Option<T> {
		return this/* : Main */.infix/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<number>, arg3 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(input/* : string */, infix/* : string */, this/* : Main */.findFirst/* : unknown */, mapper/* : (arg0 : string, arg1 : string) => Option<T> */)/* : Option<T> */;
	}
	split<T>(splitter : () => Option<[string, string]>, splitMapper : (arg0 : string, arg1 : string) => Option<T>) : Option<T> {
		return splitter/* : () => Option<[string, string]> */()/* : Option<[string, string]> */.flatMap/* : (arg0 : (arg0 : [string, string]) => Option<R>) => Option<R> */((splitTuple : [string, string]) => splitMapper/* : (arg0 : string, arg1 : string) => Option<T> */(splitTuple/* : [string, string] */[0/* : number */], splitTuple/* : [string, string] */[1/* : number */])/* : Option<T> */)/* : Option<R> */;
	}
	infix<T>(input : string, infix : string, locator : (arg0 : string, arg1 : string) => Option<number>, mapper : (arg0 : string, arg1 : string) => Option<T>) : Option<T> {
		return this/* : Main */.split/* : (arg0 : () => Option<[string, string]>, arg1 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(() => locator/* : (arg0 : string, arg1 : string) => Option<number> */(input/* : string */, infix/* : string */)/* : Option<number> */.map/* : (arg0 : (arg0 : number) => R) => Option<R> */((index : number) => {
			let left = input/* : string */.substring/* : unknown */(0/* : number */, index/* : number */)/* : unknown */;
			let right = input/* : string */.substring/* : unknown */(index/* : number */ + Strings/* : Strings */.length/* : unknown */(infix/* : string */)/* : unknown */)/* : unknown */;
			return [left/* : unknown */, right/* : unknown */];
		})/* : Option<R> */, mapper/* : (arg0 : string, arg1 : string) => Option<T> */)/* : Option<T> */;
	}
	findFirst(input : string, infix : string) : Option<number> {
		let index = input/* : string */.indexOf/* : unknown */(infix/* : string */)/* : unknown */;
		if (index/* : unknown */ === -1/* : number */){
			return new None<number>()/* : None<number> */;
		}
		return new Some(index/* : unknown */)/* : Some */;
	}
}
/*  */