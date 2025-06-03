import { Option } from "../../../magmac/api/Option";
import { EmptyHead } from "../../../magmac/api/head/EmptyHead";
import { HeadedIter } from "../../../magmac/api/head/HeadedIter";
import { RangeHead } from "../../../magmac/api/head/RangeHead";
import { SingleHead } from "../../../magmac/api/head/SingleHead";
export class Iters {
	fromArray(array : T[]) : Iter<T> {return new HeadedIter<>( new RangeHead( 0.length)).map( 0);;}
	fromOption(option : Option<T>) : Iter<T> {return 0( 0).orElseGet( 0);;}
	fromValues(...values : T[]) : Iter<T> {return 0.fromArray( 0);;}
	empty() : Iter<T> {return new HeadedIter<>( new EmptyHead<>( ));;}
}
