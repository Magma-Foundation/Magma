// [Lists, Console, Files, Characters, Strings, Actual, Namespace, Collector, EmptyHead, FlatMapHead, Head, HeadedQuery, MapHead, RangeHead, SingleHead, ZipHead, Joiner, List, ListCollector, Queries, Query, IOError, Path, None, Option, Some, Err, Ok, Result, Tuple2, Tuple2Impl, Type, CompileState, Definition, FunctionHeader, Parameter, FunctionSegment, ImmutableCompileState, Import, DivideState, Placeholder, Whitespace, ArrayType, BooleanType, FunctionType, PrimitiveType, SliceType, TemplateType, VariadicType, AccessNode, Argument, Caller, ConstructionCaller, ConstructorHeader, InvokableNode, LambdaNode, NotNode, OperationNode, StringNode, SymbolNode, Value, Location, Platform, Source]
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
