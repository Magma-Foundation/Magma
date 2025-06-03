export class Access<T,  V> {
	 Access( type : T,  receiver : V,  property : String) : public {this.type=type;this.receiver=receiver;this.property=property;;}
	public type() : T {return type;;}
	public receiver() : V {return receiver;;}
	public property() : String {return property;;}
}
