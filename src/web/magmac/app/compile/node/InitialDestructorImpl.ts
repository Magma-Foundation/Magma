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
	withNodeList(key : String, deserializer : Deserializer<T>) : CompoundDestructor<List<T>> {return new CompoundDestructorImpl<>( this.node.removeNodeListOrError( key).flatMapValue( 0));;}
	withString(key : String) : CompoundDestructor<String> {return new CompoundDestructorImpl<>( this.node.removeString( key));;}
	withNode(key : String, deserializer : Function<Node, CompileResult<T>>) : CompoundDestructor<T> {return new CompoundDestructorImpl<>( this.node.removeNodeOrError( key).flatMapValue( 0));;}
	complete(supplier : Supplier<T>) : CompileResult<T> {if(true){ return CompileResults.NodeErr( "Fields still present", this.node);;}return CompileResults.Ok( supplier.get( ));;}
}
