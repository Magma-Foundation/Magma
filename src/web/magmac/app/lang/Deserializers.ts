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
	orError : CompileResult<T>;
	wrap : CompileError;
	or : Option<CompileResult<T>>;
	wrap : TypedDeserializer<R>;
	deserializeWithType : Option<InitialDestructor>;
	destruct : InitialDestructor;
}
