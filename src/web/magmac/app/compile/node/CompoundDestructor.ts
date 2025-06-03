import { Option } from "../../../../magmac/api/Option";
import { Tuple2 } from "../../../../magmac/api/Tuple2";
import { List } from "../../../../magmac/api/collect/list/List";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { Function } from "../../../../java/util/function/Function";
export interface CompoundDestructor<T> {
	 complete( mapper : Function<T, R>) : CompileResult<R>;
	 withNodeList( key : String,  deserializer : Function<Node, CompileResult<R>>) : CompoundDestructor<Tuple2<T, List<R>>>;
	 withNode( key : String,  deserializer : Function<Node, CompileResult<R>>) : CompoundDestructor<Tuple2<T, R>>;
	 withNodeOptionally( key : String,  deserializer : Function<Node, CompileResult<R>>) : CompoundDestructor<Tuple2<T, Option<R>>>;
	 withNodeListOptionally( key : String,  deserializer : Function<Node, CompileResult<R>>) : CompoundDestructor<Tuple2<T, Option<List<R>>>>;
}
