import { None } from "../../../magmac/api/None";
import { Iter } from "../../../magmac/api/iter/Iter";
import { Option } from "../../../magmac/api/Option";
import { Function } from "../../../java/util/function/Function";
export class FlatMapHead {private final head : Head<T>;private final mapper : Function<T, Iter<R>>;private current : Iter<R>;
	 FlatMapHead( head : Head<T>,  mapper : Function<T, Iter<R>>,  initial : Iter<R>) : public {
		this.mapper=mapper;
		this.head=head;
		this.current=initial;
	}
	public next() : Option<R> {
		if(true){ 
		 maybeInner : Option<R>=this.current.next( );
		if(maybeInner.isPresent( )){ 
		return maybeInner;}
		 maybeOuter : Option<T>=this.head.next( );
		if(maybeOuter.isEmpty( )){ 
		return new None<>( );}
		this.current=this.mapper.apply( maybeOuter.orElse( null));}
	}
}
