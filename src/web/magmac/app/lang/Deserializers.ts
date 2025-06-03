import { Option } from "../../../magmac/api/Option";
import { List } from "../../../magmac/api/collect/list/List";
import { Lists } from "../../../magmac/api/collect/list/Lists";
import { Iters } from "../../../magmac/api/iter/Iters";
import { CompileResult } from "../../../magmac/app/compile/error/CompileResult";
import { CompileResults } from "../../../magmac/app/compile/error/CompileResults";
import { NodeContext } from "../../../magmac/app/compile/error/context/NodeContext";
import { CompileError } from "../../../magmac/app/compile/error/error/CompileError";
import { InitialDestructor } from "../../../magmac/app/compile/node/InitialDestructor";
import { Node } from "../../../magmac/app/compile/node/Node";
import { ImmutableCompileError } from "../../../magmac/app/error/ImmutableCompileError";
import { Annotation } from "../../../magmac/app/lang/common/Annotation";
import { JavaLang } from "../../../magmac/app/lang/java/JavaLang";
import { Modifier } from "../../../magmac/app/lang/node/Modifier";
import { TypedDeserializer } from "../../../magmac/app/lang/node/TypedDeserializer";
import { TypescriptLang } from "../../../magmac/app/lang/web/TypescriptLang";
export class Deserializers {
	orError(type : String, node : Node, deserializers : List<TypedDeserializer<T>>) : CompileResult<T> {return 0;;}
	wrap(type : String, node : Node, err : CompileError) : CompileError {return 0;;}
	or(node : Node, deserializers : List<TypedDeserializer<T>>) : Option<CompileResult<T>> {return 0;;}
	wrap(deserializer : TypedDeserializer<T>) : TypedDeserializer<R> {return 0;;}
	deserialize0(deserialize : InitialDestructor) : CompileResult<CommonLang.Definition<JavaLang.JavaType>> {return 0;;}
}
