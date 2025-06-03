export class Conditional<V> {
	Conditional(type : ConditionalType, condition : V) : public {this.type=type;this.condition=condition;;}
	type() : ConditionalType {return type;;}
	condition() : V {return condition;;}
}
