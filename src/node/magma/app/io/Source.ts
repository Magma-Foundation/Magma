// [Lists, Lists, Lists, Console, Console, Console, Files, Files, Files, Characters, Characters, Characters, Strings, Strings, Strings, Actual, Actual, Actual, Namespace, Namespace, Namespace, Collector, Collector, Collector, EmptyHead, EmptyHead, EmptyHead, FlatMapHead, FlatMapHead, FlatMapHead, Head, Head, Head, HeadedQuery, HeadedQuery, HeadedQuery, MapHead, MapHead, MapHead, RangeHead, RangeHead, RangeHead, SingleHead, SingleHead, SingleHead, ZipHead, ZipHead, ZipHead, Joiner, Joiner, Joiner, List, List, List, ListCollector, ListCollector, ListCollector, Queries, Queries, Queries, Query, Query, Query, IOError, IOError, IOError, Path, Path, Path, None, None, None, Option, Option, Option, Some, Some, Some, Err, Err, Err, Ok, Ok, Ok, Result, Result, Result, Tuple2, Tuple2, Tuple2, Tuple2Impl, Tuple2Impl, Tuple2Impl, Type, Type, Type, CompileState, CompileState, CompileState, Definition, Definition, Definition, FunctionHeader, FunctionHeader, FunctionHeader, Parameter, Parameter, Parameter, FunctionSegment, FunctionSegment, FunctionSegment, ImmutableCompileState, ImmutableCompileState, ImmutableCompileState, Import, Import, Import, DivideState, DivideState, DivideState, Placeholder, Placeholder, Placeholder, Whitespace, Whitespace, Whitespace, ArrayType, ArrayType, ArrayType, BooleanType, BooleanType, BooleanType, FunctionType, FunctionType, FunctionType, PrimitiveType, PrimitiveType, PrimitiveType, SliceType, SliceType, SliceType, TemplateType, TemplateType, TemplateType, VariadicType, VariadicType, VariadicType, AccessNode, AccessNode, AccessNode, Argument, Argument, Argument, Caller, Caller, Caller, ConstructionCaller, ConstructionCaller, ConstructionCaller, ConstructorHeader, ConstructorHeader, ConstructorHeader, InvokableNode, InvokableNode, InvokableNode, LambdaNode, LambdaNode, LambdaNode, NotNode, NotNode, NotNode, OperationNode, OperationNode, OperationNode, StringNode, StringNode, StringNode, SymbolNode, SymbolNode, SymbolNode, Value, Value, Value, Location, Location, Location, Platform, Platform, Platform, Source]
import { Path } from "../../../magma/api/io/Path";
import { IOError } from "../../../magma/api/io/IOError";
import { Result } from "../../../magma/api/result/Result";
import { List } from "../../../magma/api/collect/list/List";
import { ListCollector } from "../../../magma/api/collect/list/ListCollector";
import { Location } from "../../../magma/app/io/Location";
export class Source {
	sourceDirectory: Path;
	source: Path;
	constructor (sourceDirectory: Path, source: Path) {
		this.sourceDirectory = sourceDirectory;
		this.source = source;
	}
	read(): Result<string, IOError> {
		return this/*auto*/.source.readString(/*auto*/);
	}
	computeName(): string {
		let fileName = this/*auto*/.source.findFileName(/*auto*/);
		let separator = fileName/*auto*/.lastIndexOf(".");
		return fileName/*auto*/.substring(0/*auto*/, separator/*auto*/);
	}
	computeNamespace(): List<string> {
		return this/*auto*/.sourceDirectory.relativize(this/*auto*/.source).getParent(/*auto*/).query(/*auto*/).collect(new ListCollector<string>(/*auto*/));
	}
	computeLocation(): Location {
		return new Location(this/*auto*/.computeNamespace(/*auto*/), this/*auto*/.computeName(/*auto*/));
	}
}
