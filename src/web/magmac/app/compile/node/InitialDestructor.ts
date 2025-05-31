import { List } from "../../../../magmac/api/collect/list/List";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { Deserializer } from "../../../../magmac/app/lang/node/Deserializer";
import { Function } from "../../../../java/util/function/Function";
import { Supplier } from "../../../../java/util/function/Supplier";
export interface InitialDestructor {
	withNodeList(key : String, deserializer : Deserializer<T>) : CompoundDestructor<List<T>>;
	withString(key : String) : CompoundDestructor<String>;
	withNode(key : String, deserializer : Function<Node, CompileResult<T>>) : CompoundDestructor<T>;
	complete(supplier : Supplier<T>) : CompileResult<T>;
}
