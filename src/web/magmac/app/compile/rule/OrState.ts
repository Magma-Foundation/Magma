import { None } from "../../../../magmac/api/None";
import { Option } from "../../../../magmac/api/Option";
import { Some } from "../../../../magmac/api/Some";
import { List } from "../../../../magmac/api/collect/list/List";
import { Lists } from "../../../../magmac/api/collect/list/Lists";
import { Err } from "../../../../magmac/api/result/Err";
import { Ok } from "../../../../magmac/api/result/Ok";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { CompileResults } from "../../../../magmac/app/compile/error/CompileResults";
import { Context } from "../../../../magmac/app/compile/error/context/Context";
import { CompileError } from "../../../../magmac/app/compile/error/error/CompileError";
import { ImmutableCompileError } from "../../../../magmac/app/error/ImmutableCompileError";
export class OrState<T> {
	constructor() {0( new None<T>( ), 0.empty( ));;}
	withValue(value : T) : OrState<T> {if(true){ return 0;;}return new OrState<>( new Some<T>( 0), 0.errors);;}
	toResult(context : Context) : CompileResult<T> {return 0.maybeValue.map( 0).orElseGet( 0);;}
	withError(error : CompileError) : OrState<T> {return new OrState<>( 0.maybeValue, 0.errors.addLast( 0));;}
}
