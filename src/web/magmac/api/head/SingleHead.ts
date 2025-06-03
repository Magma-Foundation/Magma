export class SingleHead<T> {
	 SingleHead( element : T) : public {this.element=element;this.retrieved=false;;}
	public next() : Option<T> {if(true){ return new None<>( );;}this.retrieved=true;return new Some<>( this.element);;}
}
