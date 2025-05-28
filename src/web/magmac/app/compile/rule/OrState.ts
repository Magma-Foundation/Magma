import { None } from "../../../../magmac/api/None";
import { Option } from "../../../../magmac/api/Option";
import { Some } from "../../../../magmac/api/Some";
import { List } from "../../../../magmac/api/collect/list/List";
import { Lists } from "../../../../magmac/api/collect/list/Lists";
import { Err } from "../../../../magmac/api/result/Err";
import { Ok } from "../../../../magmac/api/result/Ok";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { InlineCompileResult } from "../../../../magmac/app/compile/error/InlineCompileResult";
import { Context } from "../../../../magmac/app/compile/error/context/Context";
import { CompileError } from "../../../../magmac/app/compile/error/error/CompileError";
import { ImmutableCompileError } from "../../../../magmac/app/error/ImmutableCompileError";
export class OrState {
	constructor() {
		this( new None<T>( ), Lists.empty( ));
	}
	 withValue( value : T) : OrState<T> {
		if(this.maybeValue.isPresent( )){ 
		return this;}
		return new OrState<>( new Some<T>( value), this.errors);
	}
	 toResult( context : Context) : CompileResult<T> {
		return this.maybeValue.map( ( value : T) => InlineCompileResult.fromResult( new Ok<>( value))).orElseGet( ( )->InlineCompileResult.fromResult( new Err<>( new ImmutableCompileError( "Invalid combination", context, this.errors))));
	}
	 withError( error : CompileError) : OrState<T> {
		return new OrState<>( this.maybeValue, this.errors.add( error));
	}
}
