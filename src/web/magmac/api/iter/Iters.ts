import { Option } from "../../../magmac/api/Option";
import { EmptyHead } from "../../../magmac/api/head/EmptyHead";
import { HeadedIter } from "../../../magmac/api/head/HeadedIter";
import { RangeHead } from "../../../magmac/api/head/RangeHead";
import { SingleHead } from "../../../magmac/api/head/SingleHead";
export class Iters {
	private static fromArray( array : T[]) : Iter<T> {return new HeadedIter<>( new RangeHead( array.length)).map( 0);;}
	public static fromOption( option : Option<T>) : Iter<T> {return option.<Iter<T>>map( 0).orElseGet( 0);;}
	public static fromValues( ...values : T[]) : Iter<T> {return Iters.fromArray( values);;}
	public static empty() : Iter<T> {return new HeadedIter<>( new EmptyHead<>( ));;}
}
