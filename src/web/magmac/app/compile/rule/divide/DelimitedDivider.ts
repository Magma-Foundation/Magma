export class DelimitedDivider {
	public divide( input : String) : Iter<String> {return Iters.fromValues( input.split( Pattern.quote( this.delimiter)));;}
	public createDelimiter() : String {return this.delimiter;;}
}
