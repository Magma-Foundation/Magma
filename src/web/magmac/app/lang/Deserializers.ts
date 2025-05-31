import { None } from "../../../magmac/api/None";
import { Option } from "../../../magmac/api/Option";
import { Some } from "../../../magmac/api/Some";
import { List } from "../../../magmac/api/collect/list/List";
import { Lists } from "../../../magmac/api/collect/list/Lists";
import { Iters } from "../../../magmac/api/iter/Iters";
import { CompileResult } from "../../../magmac/app/compile/error/CompileResult";
import { CompileResults } from "../../../magmac/app/compile/error/CompileResults";
import { NodeContext } from "../../../magmac/app/compile/error/context/NodeContext";
import { CompileError } from "../../../magmac/app/compile/error/error/CompileError";
import { InitialDestructor } from "../../../magmac/app/compile/node/InitialDestructor";
import { InitialDestructorImpl } from "../../../magmac/app/compile/node/InitialDestructorImpl";
import { Node } from "../../../magmac/app/compile/node/Node";
import { ImmutableCompileError } from "../../../magmac/app/error/ImmutableCompileError";
import { TypedDeserializer } from "../../../magmac/app/lang/node/TypedDeserializer";
export class Deserializers {
	orError(type : String, node : Node, deserializers : List<TypedDeserializer<T>>) : CompileResult<T>;
	wrap(type : String, node : Node, err : CompileError) : CompileError;
	or(node : Node, deserializers : List<TypedDeserializer<T>>) : Option<CompileResult<T>>;
	wrap(deserializer : TypedDeserializer<T>) : TypedDeserializer<R>;
	deserializeWithType(node : Node, type : String) : Option<InitialDestructor>;
	destruct(node : Node) : InitialDestructor;
}
