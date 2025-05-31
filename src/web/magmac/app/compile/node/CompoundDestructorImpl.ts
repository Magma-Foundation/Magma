import { None } from "../../../../magmac/api/None";
import { Option } from "../../../../magmac/api/Option";
import { Some } from "../../../../magmac/api/Some";
import { Tuple2 } from "../../../../magmac/api/Tuple2";
import { List } from "../../../../magmac/api/collect/list/List";
import { ListCollector } from "../../../../magmac/api/iter/collect/ListCollector";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { CompileResultCollector } from "../../../../magmac/app/compile/error/CompileResultCollector";
import { CompileResults } from "../../../../magmac/app/compile/error/CompileResults";
import { Function } from "../../../../java/util/function/Function";
export class CompoundDestructorImpl<T> {
	CompoundDestructorImpl(result : CompileResult<Tuple2<Node, T>>) : public;
	complete(mapper : Function<T, R>) : CompileResult<R>;
	withNodeList(key : String, deserializer : Function<Node, CompileResult<R>>) : CompoundDestructor<Tuple2<T, List<R>>>;
	withNode(key : String, deserializer : Function<Node, CompileResult<R>>) : CompoundDestructor<Tuple2<T, R>>;
	withNodeListOptionally(key : String, deserializer : Function<Node, CompileResult<R>>) : CompoundDestructor<Tuple2<T, Option<List<R>>>>;
	mapElements(current : Node, elements : NodeList, deserializer : Function<Node, CompileResult<R>>, more : T) : CompileResult<Tuple2<Node, Tuple2<T, Option<List<R>>>>>;
}
