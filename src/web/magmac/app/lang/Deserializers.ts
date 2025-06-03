import { Option } from "../../../magmac/api/Option";
import { List } from "../../../magmac/api/collect/list/List";
import { Lists } from "../../../magmac/api/collect/list/Lists";
import { Iters } from "../../../magmac/api/iter/Iters";
import { CompileResult } from "../../../magmac/app/compile/error/CompileResult";
import { CompileResults } from "../../../magmac/app/compile/error/CompileResults";
import { NodeContext } from "../../../magmac/app/compile/error/context/NodeContext";
import { CompileError } from "../../../magmac/app/compile/error/error/CompileError";
import { Node } from "../../../magmac/app/compile/node/Node";
import { ImmutableCompileError } from "../../../magmac/app/error/ImmutableCompileError";
import { TypedDeserializer } from "../../../magmac/app/lang/node/TypedDeserializer";
export class Deserializers {
	public static orError( type : String,  node : Node,  deserializers : List<TypedDeserializer<T>>) : CompileResult<T> {return Deserializers.or( node, deserializers).map( 0).orElseGet( 0);;}
	private static wrap( type : String,  node : Node,  err : CompileError) : CompileError {return new ImmutableCompileError( "Invalid type '" + type + "'", new NodeContext( node), Lists.of( err));;}
	public static or( node : Node,  deserializers : List<TypedDeserializer<T>>) : Option<CompileResult<T>> {return deserializers.iter( ).map( 0).flatMap( Iters.fromOption).next( );;}
	public static wrap( deserializer : TypedDeserializer<T>) : TypedDeserializer<R> {return 0;;}
}
