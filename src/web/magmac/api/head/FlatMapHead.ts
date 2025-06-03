import { None } from "../../../magmac/api/None";
import { Iter } from "../../../magmac/api/iter/Iter";
import { Option } from "../../../magmac/api/Option";
import { Function } from "../../../java/util/function/Function";
export class FlatMapHead<T,  R> {
	FlatMapHead(head : Head<T>, mapper : Function<T, Iter<R>>, initial : Iter<R>) : public {this.mapper=mapper;this.head=head;this.current=initial;;}
	next() : Option<R> {if(true){ maybeInner : Option<R>=this.current.next( );if(true){ return maybeInner;;}maybeOuter : Option<T>=this.head.next( );if(true){ return new None<>( );;}this.current=this.mapper.apply( maybeOuter.orElse( null));;};}
}
