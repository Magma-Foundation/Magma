import { Tuple2 } from "../../../magmac/api/Tuple2";
import { Collector } from "../../../magmac/api/iter/collect/Collector";
export class TupleCollector<A,  AC,  B,  BC> {
	createInitial() : Tuple2<AC, BC> {return new Tuple2<>( this.leftCollector.createInitial( ), this.rightCollector.createInitial( ));;}
	fold(current : Tuple2<AC, BC>, element : Tuple2<A, B>) : Tuple2<AC, BC> {break;break;return new Tuple2<>( leftValue, rightValue);;}
}
