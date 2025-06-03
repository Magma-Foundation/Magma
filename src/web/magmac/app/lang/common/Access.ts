export class Access<V,  R,  T> {
	 Access( variant : V,  receiver : R,  property : String,  maybeArguments : Option<List<T>>) : public {this.variant=variant;this.receiver=receiver;this.property=property;this.maybeArguments=maybeArguments;;}
	public type() : V {return this.variant;;}
	public receiver() : R {return this.receiver;;}
	public property() : String {return this.property;;}
}
