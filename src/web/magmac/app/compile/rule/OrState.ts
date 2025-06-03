export class OrState<T> {
	constructor() {this( new None<T>( ), Lists.empty( ));;}
	 withValue( value : T) : OrState<T> {if(true){ return this;;}return new OrState<>( new Some<T>( value), this.errors);;}
	 toResult( context : Context) : CompileResult<T> {return this.maybeValue.map( 0).orElseGet( ( )->CompileResults.fromResult( new Err<>( new ImmutableCompileError( "Invalid combination", context, this.errors))));;}
	 withError( error : CompileError) : OrState<T> {return new OrState<>( this.maybeValue, this.errors.addLast( error));;}
}
