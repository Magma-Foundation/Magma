export class AbstractReturnNode<V> {
	AbstractReturnNode(child : V) : public {this.child=child;;}
	child() : V {return child;;}
}
