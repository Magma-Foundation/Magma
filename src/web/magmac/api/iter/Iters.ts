import { Option } from "../../../magmac/api/Option";
import { EmptyHead } from "../../../magmac/api/head/EmptyHead";
import { HeadedIter } from "../../../magmac/api/head/HeadedIter";
import { RangeHead } from "../../../magmac/api/head/RangeHead";
import { SingleHead } from "../../../magmac/api/head/SingleHead";
export class Iters {
	private static fromArray() : Iter<T> {
		return new HeadedIter<>( new RangeHead( array.length)).map( ( index() : Integer) => array[index]);
	}
	public static fromOption( option() : Option<T>) : Iter<T> {
		return option.<Iter<T>>map( ( t() : T) => new HeadedIter<>( new SingleHead<>( t))).orElseGet( ( )->new HeadedIter<>( new EmptyHead<>( )));
	}
	public static fromValues() : Iter<T> {
		return Iters.fromArray( values);
	}
	public static empty() : Iter<T> {
		return new HeadedIter<>( new EmptyHead<>( ));
	}
}
