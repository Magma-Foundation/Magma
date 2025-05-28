import { Option } from "../../../magmac/api/Option";
import { EmptyHead } from "../../../magmac/api/head/EmptyHead";
import { HeadedIter } from "../../../magmac/api/head/HeadedIter";
import { RangeHead } from "../../../magmac/api/head/RangeHead";
import { SingleHead } from "../../../magmac/api/head/SingleHead";
export class Iters {
	fromArray : Iter<T> {
		return new HeadedIter<>( new RangeHead( array.length)).map( (Integer index) ->array[index]);
	}
	fromOption(option : Option<T>) : Iter<T> {
		return option.<Iter<T>>map( (T t) ->new HeadedIter<>( new SingleHead<>( t))).orElseGet( () ->new HeadedIter<>( new EmptyHead<>( )));
	}
	fromValues : Iter<T> {
		return Iters.fromArray( values);
	}
	empty() : Iter<T> {
		return new HeadedIter<>( new EmptyHead<>( ));
	}
}
