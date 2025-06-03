export class Block<H,  S> {
	 Block( header : H,  children : List<S>) : public {this.header=header;this.children=children;;}
	public header() : H {return this.header;;}
	public segments() : List<S> {return this.children;;}
}
