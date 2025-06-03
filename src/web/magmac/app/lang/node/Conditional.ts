export class Conditional<V> {
	 Conditional( type : ConditionalType,  condition : V) : public {this.type=type;this.condition=condition;;}
	public type() : ConditionalType {return type;;}
	public condition() : V {return condition;;}
}
