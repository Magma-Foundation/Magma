export class Access<T,  V> {
	Access(type : T, receiver : V, property : String) : public {this.type=type;this.receiver=receiver;this.property=property;;}
	type() : T {return type;;}
	receiver() : V {return receiver;;}
	property() : String {return property;;}
}
