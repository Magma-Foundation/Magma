export class FlatMapHead<T,  R> {
	 FlatMapHead( head : Head<T>,  mapper : Function<T, Iter<R>>,  initial : Iter<R>) : public {this.mapper=mapper;this.head=head;this.current=initial;;}
	public next() : Option<R> {if(true){  let maybeInner : var=this.current.next( );if(true){ return maybeInner;;} let maybeOuter : var=this.head.next( );if(true){ return new None<>( );;}this.current=this.mapper.apply( maybeOuter.orElse( null));;};}
}
