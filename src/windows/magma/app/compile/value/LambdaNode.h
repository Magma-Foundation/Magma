// [Lists, Lists, Lists, Console, Console, Console, Files, Files, Files, Characters, Characters, Characters, Strings, Strings, Strings, Actual, Actual, Actual, Namespace, Namespace, Namespace, Collector, Collector, Collector, EmptyHead, EmptyHead, EmptyHead, FlatMapHead, FlatMapHead, FlatMapHead, Head, Head, Head, HeadedQuery, HeadedQuery, HeadedQuery, MapHead, MapHead, MapHead, RangeHead, RangeHead, RangeHead, SingleHead, SingleHead, SingleHead, ZipHead, ZipHead, ZipHead, Joiner, Joiner, Joiner, List, List, List, ListCollector, ListCollector, ListCollector, Queries, Queries, Queries, Query, Query, Query, IOError, IOError, IOError, Path, Path, Path, None, None, None, Option, Option, Option, Some, Some, Some, Err, Err, Err, Ok, Ok, Ok, Result, Result, Result, Tuple2, Tuple2, Tuple2, Tuple2Impl, Tuple2Impl, Tuple2Impl, Type, Type, Type, CompileState, CompileState, CompileState, Definition, Definition, Definition, FunctionHeader, FunctionHeader, FunctionHeader, Parameter, Parameter, Parameter, FunctionSegment, FunctionSegment, FunctionSegment, ImmutableCompileState, ImmutableCompileState, ImmutableCompileState, Import, Import, Import, DivideState, DivideState, DivideState, Placeholder, Placeholder, Placeholder, Whitespace, Whitespace, Whitespace, ArrayType, ArrayType, ArrayType, BooleanType, BooleanType, BooleanType, FunctionType, FunctionType, FunctionType, PrimitiveType, PrimitiveType, PrimitiveType, SliceType, SliceType, SliceType, TemplateType, TemplateType, TemplateType, VariadicType, VariadicType, VariadicType, AccessNode, AccessNode, AccessNode, Argument, Argument, Argument, Caller, Caller, Caller, ConstructionCaller, ConstructionCaller, ConstructionCaller, ConstructorHeader, ConstructorHeader, ConstructorHeader, InvokableNode, InvokableNode, InvokableNode, LambdaNode, LambdaNode, LambdaNode]
#ifndef magma_app_compile_value_LambdaNode
#define magma_app_compile_value_LambdaNode
import { Value } from "magma/app/compile/value/Value";
import { Definition } from "magma/app/compile/define/Definition";
import { List } from "magma/api/collect/list/List";
import { Platform } from "magma/app/io/Platform";
import { Joiner } from "magma/api/collect/Joiner";
import { Option } from "magma/api/option/Option";
import { Some } from "magma/api/option/Some";
import { None } from "magma/api/option/None";
import { Type } from "magma/api/Type";
import { PrimitiveType } from "magma/app/compile/type/PrimitiveType";
#endif
