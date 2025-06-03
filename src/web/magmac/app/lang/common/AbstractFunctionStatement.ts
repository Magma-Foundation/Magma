export class AbstractFunctionStatement<V> {
	AbstractFunctionStatement(child : V) : public {this.child=child;;}
	child() : V {return this.child;;}
}
