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
	InitialDestructorImpl(node : Node) : public {break;;}
	withNodeList(key : String, deserializer : Deserializer<T>) : CompoundDestructor<List<T>> {return new CompoundDestructorImpl<>( 0.node.removeNodeListOrError( 0).flatMapValue( 0));;}
	withString(key : String) : CompoundDestructor<String> {return new CompoundDestructorImpl<>( 0.node.removeString( 0));;}
	withNode(key : String, deserializer : Function<Node, CompileResult<T>>) : CompoundDestructor<T> {return new CompoundDestructorImpl<>( 0.node.removeNodeOrError( 0).flatMapValue( 0));;}
	complete(supplier : Supplier<T>) : CompileResult<T> {if(true){ return 0.NodeErr( "Fields still present", 0.node);;}return 0.Ok( 0.get( ));;}
}
