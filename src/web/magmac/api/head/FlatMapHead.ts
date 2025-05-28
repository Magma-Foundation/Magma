import { None } from "../../../magmac/api/None";
import { Iter } from "../../../magmac/api/iter/Iter";
import { Option } from "../../../magmac/api/Option";
import { Function } from "../../../java/util/function/Function";
export class FlatMapHead {
	temp : ?;
	temp : ?;
	temp : ?;
	FlatMapHead(head : Head<T>, mapper : Function<T, Iter<R>>, initial : Iter<R>) : public {this.mapper=mapper;this.head=head;this.current=initial;}
	next() : Option<R> {if(true){  Option<R> maybeInner=this.current.next( );if(maybeInner.isPresent( )){ return maybeInner;} Option<T> maybeOuter=this.head.next( );if(maybeOuter.isEmpty( )){ return new None<>( );}this.current=this.mapper.apply( maybeOuter.orElse( null));}}
}
