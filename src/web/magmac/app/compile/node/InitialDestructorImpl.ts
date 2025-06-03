import { Tuple2 } from "../../../../magmac/api/Tuple2";
import { List } from "../../../../magmac/api/collect/list/List";
import { ListCollector } from "../../../../magmac/api/iter/collect/ListCollector";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { CompileResultCollector } from "../../../../magmac/app/compile/error/CompileResultCollector";
import { CompileResults } from "../../../../magmac/app/compile/error/CompileResults";
import { Deserializer } from "../../../../magmac/app/lang/node/Deserializer";
import { Function } from "../../../../java/util/function/Function";
import { Supplier } from "../../../../java/util/function/Supplier";
export class InitialDestructorImpl {
	InitialDestructorImpl(node : Node) : public {;;}
	withNodeList(key : String, deserializer : Deserializer<T>) : CompoundDestructor<List<T>> {;;}
	withString(key : String) : CompoundDestructor<String> {;;}
	withNode(key : String, deserializer : Function<Node, CompileResult<T>>) : CompoundDestructor<T> {;;}
	complete(supplier : Supplier<T>) : CompileResult<T> {;;;}
}
